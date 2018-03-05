/// <reference path="../../reference.ts"/>

interface JQuery {
    ntsDatepicker(action: string, param?: any): any;
}

module nts.uk.ui.jqueryExtentions {

    module ntsDatepicker {
        $.fn.ntsDatepicker = function(action: string, index?: number): any {
            var $container = $(this);
            if (action === "bindFlip") {
                return bindFlip($container);
            }
            return $container;
        }

        function bindFlip($input: JQuery): JQuery{
            var datepickerID = $input.attr("id");
            
            $input.on('show.datepicker', function (evt) { 
                $input.data("showed", true);
                setTimeout(function (){
                    $input.trigger("flippickercontainer");
                }, 10);
            });
            $input.on('hide.datepicker', function (evt) {
                $input.data("showed", false); 
//                let currentShowContainer = $(".datepicker-container:not(.datepicker-hide)");
//                $("body").append(currentShowContainer);
            });
            $( window ).resize(function() {
                if($input.data("showed")){
                    $input.datepicker('hide');
                    setTimeout(function (){
                        $input.datepicker('show');
                    }, 10);   
                }
            });
            
            $input.bind("flippickercontainer", function(evt, data){
                let currentShowContainer = $(".datepicker-container:not(.datepicker-hide)");
                let container = $input.parent();
//                container.append(currentShowContainer);
                let ePos = container.offset();
                if(ePos.top < 0 && ePos.left < 0){
                    return;
                }
                let containerHeight = container.outerHeight(true); 
                let containerWidth = container.outerWidth(true);
                let showContainerHeight = currentShowContainer.outerHeight(true);
                let showContainerWidth = currentShowContainer.outerWidth(true);
                let documentHeight = document.body.clientHeight;
                let documentWidth = document.body.clientWidth;
                let headerHeight = $("#functions-area").outerHeight(true) + $("#header").outerHeight(true);
                let bottomHeight = $("#functions-area-bottom").outerHeight(true);
                let spaceBottom = documentHeight - ePos.top - containerHeight;
                let spaceTop = ePos.top - headerHeight;
                let spaceRight = documentWidth - ePos.left - containerWidth;
                let spaceLeft = ePos.left;
                currentShowContainer.removeClass();
                currentShowContainer.addClass("datepicker-container datepicker-dropdown");
                // case 1: show below
                if(showContainerHeight + 10 <= spaceBottom){
                    //currentShowContainer.css({top: containerHeight + 5, left: 0});
                    currentShowContainer.addClass("datepicker-top-left");
                    //container.addClass("caret-bottom");
                    return;
                }
                //case 2: show above
                if(showContainerHeight + 10 <= spaceTop){
                    //currentShowContainer.css({top: 0 - showContainerHeight - 5, left: 0});
                    currentShowContainer.addClass("datepicker-bottom-left");
                    currentShowContainer.position({
                        my: "left bottom-10",
                        at: "left top",
                        'of': "#" + datepickerID
                    });
                    return;
                }
                // case 3: show right
                if(showContainerWidth + 10 <= spaceRight){
                    currentShowContainer.css({top: 0, left: containerWidth + 5 + 2});
                    currentShowContainer.addClass("datepicker-left-top");
                    return;
                }
                //case 4: show left
                if(showContainerWidth + 10 <= spaceLeft){
                    currentShowContainer.css({top: 0, left: 0 - showContainerWidth - 5 - 2 });
                    currentShowContainer.addClass("datepicker-right-top");
                    return;
                }
            });

            return $input;
        }

    }
}