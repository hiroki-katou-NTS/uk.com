/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {

    /**
     * HelpButton binding handler
     */
    class NtsIconBindingHandler implements KnockoutBindingHandler {
        
        constructor() { }
        
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext) {
            // Get data
            let data = valueAccessor();
            let iconNo: string = ko.unwrap(data.no);
            let width: string = ko.unwrap(data.width) || "100%";
            let height: string = ko.unwrap(data.height) || "100%";
            
            let iconFileName = iconNo + ".png";
            let iconPath = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["comjs"] + "/")
                .mergeRelativePath("lib/nittsu/ui/style/stylesheets/images/icons/numbered/")
                .mergeRelativePath(iconFileName)
                .serialize();
            
            let $icon = $(element),
                $parent = $icon.closest("td[role='gridcell']");
            $icon.addClass("img-icon");
            $icon.css({
                "background-image": "url(" + iconPath + ")",
                "background-size": "contain",
                width: width,
                height: height
            });
            if(!_.isNil($parent)){
                $parent.css("white-space", "nowrap");
            }
        }

        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data
            var data = valueAccessor();
        }
    }
    
    ko.bindingHandlers['ntsIcon'] = new NtsIconBindingHandler();
}
