/// <reference path="../../reference.ts"/>

interface JQuery {
    ntsFixedTable(action?: string, param?: any): any;
}

module nts.uk.ui.jqueryExtentions {

    module ntsFixedTable {

        $.fn.ntsFixedTable = ntsFixedTable;

        function ntsFixedTable(action?: string, options?: any): any {
            var $controls = $(this);
            if (typeof arguments[0] !== 'string') {
                return ntsFixedTable.apply($controls, _.concat("init", action));
            }

            if (action === "init") {
                return init($controls, options);
            }
            else {
                return $controls;
            };
        }

        function init(controls: JQuery, options?: any): JQuery {
            controls.each(function() {
                let $originTable = $(this);
                $originTable.addClass("fixed-table");
                let $colgroup = $originTable.find("colgroup");
                let $thead = $originTable.find("thead");
                let setting = $.extend({ height: "auto" }, options);
                let viewWidth = setting.width;
                let width = 0;
                $colgroup.find("col").each(function() {
                    width += Number($(this).attr("width").replace(/px/gi, ''));
                });
                width++;
                if(nts.uk.util.isNullOrUndefined(viewWidth)){
                    viewWidth = width;
                }
                
                let $container = $("<div class='nts-fixed-table cf'/>");
                $originTable.after($container);
                
                
                let $headerContainer = $("<div class='nts-fixed-header-container ui-iggrid nts-fixed-header'/>").css({"max-width": viewWidth});
                let $headerWrapper = $("<div class='nts-fixed-header-wrapper'/>").width(width);
                let $headerTable = $("<table class='fixed-table'></table>");
                
                
                $headerTable.append($colgroup.clone()).append($thead);
                $headerTable.appendTo($headerWrapper);
                $headerContainer.append($headerWrapper);
                
                let $header = $("<div>");
                $headerContainer.appendTo($header);
                $header.appendTo($container);
                $header.height($headerContainer.height());
                let $headerScroll = $("<div>", {"class": "scroll-header nts-fixed-header", width: 17, height: $headerContainer.height()}); 
                $headerScroll.appendTo($header);
                
                $originTable.addClass("nts-fixed-body-table");
                let $bodyContainer = $("<div class='nts-fixed-body-container ui-iggrid'/>");
                let $bodyWrapper = $("<div class='nts-fixed-body-wrapper'/>");
                let bodyHeight: any = "auto";
                if (setting.height !== "auto") {
                    $bodyContainer.css("max-width", viewWidth);
                    bodyHeight = Number(setting.height.toString().replace(/px/mi)) - $headerTable.find("thead").outerHeight();
                }
                
                let resizeEvent = function () {
                    $header.height($headerContainer.height());
                    if(bodyHeight < $originTable.height()){
                        if(/Edge/.test(navigator.userAgent)){
                            $headerScroll.width(12);
                            $bodyContainer.css("padding-right", "12px");
                        }else {
                            $headerScroll.width(17);
                            $bodyContainer.css("padding-right", "17px");
                        }        
                    } else {
//                        if($originTable.height() !== 0){
//                            if(/Edge/.test(navigator.userAgent)){
//                                $bodyWrapper.height($originTable.height());
//                                $bodyContainer.height($originTable.height() + 12);
//                            }else {
//                                $bodyWrapper.height($originTable.height());
//                                $bodyContainer.height($originTable.height() + 17);
//                            } 
//                            $headerScroll.width(0);   
//                            $bodyWrapper.removeClass("body-no-record");   
//                        } else {
//                            $bodyWrapper.addClass("body-no-record");    
//                        } 
                        $headerScroll.width(0);
                        $bodyContainer.css("padding-right", "0px");
                    }
                
                    setTimeout(resizeEvent, 20);
                }
                
                
                
                $bodyContainer.scroll(function(evt, ui) {
                    let bodyScroll = $bodyContainer.scrollLeft();
                    if(bodyScroll > 0){
                        bodyScroll = bodyScroll + 1.25;
                        $headerContainer.css({"border-left": "1px solid #CCC"});
                    }else {
                        $headerContainer.css({"border-left": "0px solid #CCC"});    
                    }
                    $headerContainer.scrollLeft(bodyScroll);
                    
                });
                $bodyWrapper.width(width).height(bodyHeight);
                $bodyWrapper.append($originTable);
                $bodyContainer.append($bodyWrapper);
                $container.append($bodyContainer);
                if (setting.height !== "auto" && bodyHeight < $originTable.height()){
                    if(/Edge/.test(navigator.userAgent)){
                        $bodyContainer.css("padding-right", "12px");
                    }else {
                        $bodyContainer.css("padding-right", "17px");
                    }   
                }
                resizeEvent();
            });
            return controls;
        }
    }
}