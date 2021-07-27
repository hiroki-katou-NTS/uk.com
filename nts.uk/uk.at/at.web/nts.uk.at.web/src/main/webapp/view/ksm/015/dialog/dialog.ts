
module nts.uk.at.view.ksm015 {
    
    let PS: any = window.parent;

    /**
     * Dialog Module
     * Using for display info or confirm dialog
     */
    export module dialog {
        interface DialogHeader {
            icon?: string;
            text?: string;
        }
        interface Message {
            d: string;
            messageParams?: any[];
        }
        export function getMaxZIndex() {
            let overlayElements = PS.$(".ui-widget-overlay");
            var max = 12000;
            if (overlayElements.length > 0) {
                let zIndexs = _.map(overlayElements, function(element) { return parseInt($(element).css("z-index")); });
                var temp = _.max(zIndexs);
                max = temp > max ? temp : max;
            }
            return max;
        }
        
        export function version() {
            let versinText = "AP version: ...";
            
            let $this = PS.$('<div/>').addClass('version-dialog')
                .append($('<div/>').addClass('text').append(versinText))
                .appendTo('body')
                .dialog({
                });
        }
        


        
        
        function addError(errorBody: JQuery, error: any, idx: number){
            let row = $("<tr/>");
            row.append("<td style='display: none;'>" + idx + "/td><td>" + error["message"] + "</td><td>" + error["messageId"] + "</td>");
            let nameId = error["supplements"]["NameID"];   
            if (!util.isNullOrUndefined(nameId)) {
                row.click(function(evt, ui){
                    let element = $("body").find('[NameID="' + nameId + '"]');
                    let tab = element.closest("[role='tabpanel']");
                    while(!util.isNullOrEmpty(tab)){
                        let tabId = tab.attr("id");
                        tab.siblings(":first").children("li[aria-controls='" + tabId + "']").children("a").click();
                        tab = tab.parent().closest("[role='tabpanel']");
                    }
                    element.focus();
                    let $dialogContainer = errorBody.closest(".bundled-errors-alert").closest("[role='dialog']");
                    let $self = nts.uk.ui.windows.getSelf();
                    let additonalTop = 0;
                    let additonalLeft = 0;
                    if(!$self.isRoot) {
                        let $currentDialog = $self.$dialog.closest("[role='dialog']");
                        let $currentHeadBar = $currentDialog.find(".ui-dialog-titlebar");
                        let currentDialogOffset = $currentDialog.offset();
                        additonalTop = currentDialogOffset.top+ $currentHeadBar.height();
                        additonalLeft = currentDialogOffset.left;
                    }
                    
                    let currentControlOffset = element.offset();
                    let doc = document.documentElement;
                    let scrollX = (window.pageXOffset || doc.scrollLeft) - (doc.clientLeft || 0);
                    let scrollY = (window.pageYOffset || doc.scrollTop) - (doc.clientTop || 0);
                    let top = additonalTop + currentControlOffset.top + element.outerHeight() - scrollY;
                    //                    let top = additonalTop + currentControlOffset.top  + element.outerHeight() - window.scrollY;
                    let left = additonalLeft + currentControlOffset.left - scrollX;
                    //                    let left = additonalLeft + currentControlOffset.left - window.scrollX;
                    let $errorDialogOffset = $dialogContainer.offset();
                    let maxLeft = $errorDialogOffset.left + $dialogContainer.width();
                    let maxTop = $errorDialogOffset.top + $dialogContainer.height();
                    if($errorDialogOffset.top < top && top < maxTop){
                        $dialogContainer.css("top", top + 15);
                    }
                    if (($errorDialogOffset.left < left && left < maxLeft) ){
                        $dialogContainer.css("left", left);
                    }
                });    
            }
            row.appendTo(errorBody);  
        }
        
        function getRoot(): JQuery {
            let self = nts.uk.ui.windows.getSelf();
            while(!self.isRoot){
                self = self.parent; 
            }
            return $(self.globalContext.document).find("body");
        }
        
        export function bundledErrors(errors) {
            var then = $.noop;
            let id = util.randomId();
            let container = $("<div id='" + id + "' class='bundled-errors-alert'/>"), 
                functionArea = $("<div id='functions-area-bottom'/>"),
				span = $(`<span id = 'span-info'>`+ nts.uk.resource.getMessage('Msg_15') + `</span>`),
                errorBoard = $(`<div id='error-board' style='margin-top: 10px;'>    <table > <thead> <tr>    <th style='width: auto;'>`
                     + nts.uk.resource.getText('KSM015_102') + `</th><th style='display: none;'/>    <th style='width: 60px; text-align: left'>`
                     + nts.uk.resource.getText('KSM015_103') + `</th>   </tr>   </thead>    <tbody/>    </table> </div>`),
                closeButton = $("<button class='ntsButton ntsClose large' style='margin-left: 0px !important;'/>");
            
            let errorBody = errorBoard.find("tbody");
            if($.isArray(errors["errors"])) {
                 _.forEach(errors["errors"], function(error, idx: number){ 
                    addError(errorBody, error, idx + 1);  
                 });   
            } else {
                return nts.uk.ui.dialog.alertError(errors);
            }
                       
            let dialogInfo = nts.uk.ui.windows.getSelf();
            
            closeButton.appendTo(functionArea);
			span.appendTo(container);
            errorBoard.appendTo(container);
            functionArea.appendTo(container);
            container.appendTo(getRoot());

            const cssFunctionArea = {
                "left": "0px",
                "width": "100%",
                "position": "absolute",
                "text-align": "center",
                "padding": "10px 0px",
                "border-top": "1px solid #ccc",
                "bottom": "0"
            };
            
            setTimeout(function() {
                container.dialog({ 
                    title: nts.uk.resource.getText('KSM015_101'),   
                    dialogClass: "no-close-btn",
                    modal: false,
                    resizable: false,
                    width: 450,
                    maxHeight: 500,
                    closeOnEscape: false,
                    open: function() {
                        errorBoard.css({"overflow": "auto", "max-height" : "289px", "margin": "0px 0px 65px 0px", "border": "1px solid #EAE8F2"});
                        functionArea.css(cssFunctionArea);
                        closeButton.text(nts.uk.resource.getText('KSM015_104')).click(function(evt){
                            container.dialog("destroy");  
                            container.remove();
                            then();
                        });
                        
                        container.ntsDialogEx("centerUp", dialogInfo);
                    },
                    close: function(event) {
                    }
                }).dialogPositionControl();
            }, 0);
            
            if(!dialogInfo.isRoot){
                var normalClose = dialogInfo.onClosedHandler;
                var onCloseAuto = function(){
                    normalClose();
                    if(container.dialog("isOpen")){
                        container.dialog("close");
                    }
                }
                dialogInfo.onClosedHandler = onCloseAuto;
            }
            
            return {
                then: function(callback) {
                    then = callback;
                }
            };
        };
    }
    
}
