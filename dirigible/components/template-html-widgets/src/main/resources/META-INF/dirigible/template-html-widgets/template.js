/*
 * Generated by Eclipse Dirigible based on model and template.
 *
 * Do not modify the content as it may be re-generated again.
 */
exports.getTemplate = function() {
	return {
		"name": "HTML5 Widgets (AngularJS)",
		"description": "HTML5 Widgets for AngularJS",
		"sources": [{
			"location": "/template-html-widgets/index.html.template", 
			"action": "generate",
			"rename": "{{fileName}}.html",
			"start" : "[[",
			"end" : "]]"
		},
		{
			"location": "/template-html-widgets/view.js", 
			"action": "copy"
		},
		{
			"location": "/template-html-widgets/controller.js", 
			"action": "copy"
		}],
		"parameters": [],
		"order": 30
	};
};
