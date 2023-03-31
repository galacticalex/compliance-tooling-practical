/*
 * Copyright (c) 2023 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2023 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 * SPDX-License-Identifier: EPL-2.0
 */
const resultView = angular.module('result', ['ideUI', 'ideView']);

resultView.config(["messageHubProvider", function (messageHubProvider) {
    messageHubProvider.eventIdPrefix = 'result-view';
}]);

resultView.controller('DatabaseResultController', ['$scope', '$http', function ($scope, $http) {
    $scope.state = {
        isBusy: false,
        error: false,
        busyText: "Loading...",
    };

    let messageHub = new FramesMessageHub();

    let csrfToken = null;

    $http.get("", { headers: { "X-CSRF-Token": "Fetch" } }).then(function (response) {
        csrfToken = response.headers()["x-csrf-token"];
    }, function (response) {
        console.error("Error getting token.", response);
    });

    // $scope.database = "metadata";
    $scope.datasource = "DefaultDB";

    messageHub.subscribe(function (evt) {
        $scope.database = evt.data;
    }, "database.database.selection.changed");

    messageHub.subscribe(function (evt) {
        $scope.datasource = evt.data;
    }, "database.datasource.selection.changed");

    messageHub.subscribe(function (command) {
        $scope.state.error = false;
        $scope.state.isBusy = true;
        let url = "/services/data/" + $scope.datasource;
        let sql = command.data.trim().toLowerCase();
        if (sql.startsWith('select')) {
            $http({
                method: 'POST',
                url: url + "/query",
                data: command.data,
                headers: {
                    'Content-Type': 'text/plain',
                    'X-Requested-With': 'Fetch',
                    'X-CSRF-Token': csrfToken
                }
            }).then(function (result) {
                cleanScope();
                if (result.data != null && result.data.length > 0) {
                    $scope.rows = result.data;
                    $scope.columns = [];
                    for (let i = 0; i < result.data.length; i++) {
                        for (let column in result.data[i]) {
                            $scope.columns.push(column);
                        }
                        break;
                    }
                } else if (result.data !== null && result.data.errorMessage !== null && result.data.errorMessage !== undefined) {
                    $scope.state.error = true;
                    $scope.errorMessage = result.data.errorMessage;
                } else {
                    $scope.result = 'Empty result';
                }
                $scope.state.isBusy = false;
            });
        } else if (sql.startsWith('call')) {
            $http({
                method: 'POST',
                url: url + "/procedure",
                data: command.data,
                headers: {
                    'Content-Type': 'text/plain',
                    'X-Requested-With': 'Fetch',
                    'X-CSRF-Token': csrfToken
                }
            }).then(function (result) {
                cleanScope();
                if (result.data != null && result.data.length > 0) {
                    $scope.hasMultipleProcedureResults = result.data.length > 1;
                    if ($scope.hasMultipleProcedureResults) {
                        $scope.procedureResults = [];
                        for (let resultIndex = 0; resultIndex < result.data.length; resultIndex++) {
                            let procedureResult = JSON.parse(result.data[resultIndex]);
                            let data = {
                                rows: procedureResult,
                                columns: []
                            };
                            for (let i = 0; i < procedureResult.length; i++) {
                                for (let column in procedureResult[i]) {
                                    data.columns.push(column);
                                }
                                break;
                            }
                            $scope.procedureResults.push(data);
                        }
                    } else {
                        result = JSON.parse(result.data[0]);
                        $scope.rows = result;
                        $scope.columns = [];
                        for (let i = 0; i < result.length; i++) {
                            for (let column in result[i]) {
                                $scope.columns.push(column);
                            }
                            break;
                        }
                    }
                } else if (result.data !== null && result.data.errorMessage !== null && result.data.errorMessage !== undefined) {
                    $scope.state.error = true;
                    $scope.errorMessage = result.data.errorMessage;
                } else {
                    $scope.result = 'Empty result';
                }
                $scope.state.isBusy = false;
            });
        } else {
            $http({
                method: 'POST',
                url: url + "/update",
                data: command.data,
                headers: {
                    'Content-Type': 'text/plain', 'Accept': 'text/plain',
                    'X-Requested-With': 'Fetch',
                    'X-CSRF-Token': csrfToken
                }
            }).then(function (result) {
                cleanScope();
                if (!isNaN(result.data)) {
                    result = 'Rows updated: ' + result.data;
                } else if (result.data !== null) {
                    $scope.result = result.data;
                } else {
                    $scope.result = 'Empty result';
                }
                $scope.result = result.data;
                $scope.state.isBusy = false;
            });
        }
    }, "database.sql.execute");


    messageHub.subscribe(function (command) {
        let artifact = command.data.split('.');
        let url = "/services/data/export/" + $scope.datasource + "/" + artifact[0] + "/" + artifact[1];
        window.open(url);
    }, "database.data.export.artifact");

    messageHub.subscribe(function (command) {
        let schema = command.data;
        let url = "/services/data/export/" + $scope.datasource + "/" + schema;
        window.open(url);
    }, "database.data.export.schema");

    messageHub.subscribe(function (command) {
        let artifact = command.data.split('.');
        let url = "/services/data/definition/" + $scope.datasource + "/" + artifact[0] + "/" + artifact[1];
        window.open(url);
    }, "database.metadata.export.artifact");

    messageHub.subscribe(function (command) {
        let schema = command.data;
        let url = "/services/data/definition/" + $scope.datasource + "/" + schema;
        window.open(url);
    }, "database.metadata.export.schema");

    function cleanScope() {
        $scope.result = null;
        $scope.columns = null;
        $scope.rows = null;
        $scope.hasMultipleProcedureResults = false;
        $scope.procedureResults = null;
    }

}]);
