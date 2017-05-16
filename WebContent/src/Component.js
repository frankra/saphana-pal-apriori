sap.ui.define( ["sap/ui/core/UIComponent"], function (UIComponent) {
	"use strict";
	return UIComponent.extend("sap.ui.scpdemoday.AppComponent", {
 
		metadata: {
			rootView: "sap.ui.scpdemoday.views.App",
			routing: {
				config: {
					routerClass: "sap.m.routing.Router",
					viewPath: "sap.ui.scpdemoday.views",
					controlId: "AppControl",
					controlAggregation: "pages",
					viewType: "XML"
				},
				routes: [
					{
						name: "search",
						// empty hash - normally the start page
						pattern: "",
						target: "search"
					}
				],
				targets: {
					search: {
						viewName: "Search",
						viewLevel: 0
					}
				}
			}
		},
 
		init : function () {
			UIComponent.prototype.init.apply(this, arguments);
 
			// Parse the current url and display the targets of the route that matches the hash
			this.getRouter().initialize();
		}
 
	});
}, /* bExport= */ true);