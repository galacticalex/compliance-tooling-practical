/*
 * Generated by Eclipse Dirigible based on model and template.
 *
 * Do not modify the content as it may be re-generated again.
 */
exports.getSources = function (parameters) {
	return [{
		location: "/template-application-ui-angular/ui/perspectives/views/report/line/index.html.template",
		action: "generate",
		rename: "gen/ui/{{perspectiveName}}/views/{{fileName}}/index.html",
		engine: "velocity",
		collection: "uiReportLinesModels"
	}, {
		location: "/template-application-ui-angular/ui/perspectives/views/report/line/controller.js.template",
		action: "generate",
		rename: "gen/ui/{{perspectiveName}}/views/{{fileName}}/controller.js",
		engine: "velocity",
		collection: "uiReportLinesModels"
	}, {
		location: "/template-application-ui-angular/ui/perspectives/views/report/line/extensions/view.js.template",
		action: "generate",
		rename: "gen/ui/{{perspectiveName}}/views/{{fileName}}/extensions/view.js",
		collection: "uiReportLinesModels"
	}, {
		location: "/template-application-ui-angular/ui/perspectives/views/report/line/extensions/view.extension.template",
		action: "generate",
		rename: "gen/ui/{{perspectiveName}}/views/{{fileName}}/extensions/view.extension",
		collection: "uiReportLinesModels"
	}, {
		location: "/template-application-ui-angular/ui/perspectives/views/report/line/extensions/menu/item.extension.template",
		action: "generate",
		rename: "gen/ui/{{perspectiveName}}/views/{{fileName}}/extensions/menu/item.extension",
		collection: "uiReportLinesModels"
	}, {
		location: "/template-application-ui-angular/ui/perspectives/views/report/line/extensions/menu/item.js.template",
		action: "generate",
		rename: "gen/ui/{{perspectiveName}}/views/{{fileName}}/extensions/menu/item.js",
		collection: "uiReportLinesModels"
	}];
};
