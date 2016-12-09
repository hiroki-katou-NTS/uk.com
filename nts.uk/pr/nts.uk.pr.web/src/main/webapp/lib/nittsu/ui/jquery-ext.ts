interface JQuery {
    ntsPopup(args: any): JQuery;
    ntsError(action: string, param?: any): any;
}

module nts.uk.ui.jqueryExtentions {
    
    module ntsError {
        var DATA_HAS_ERROR = 'hasError';
        
        $.fn.ntsError = function (action: string, message: string): any {
            var $control = $(this);                   
            if(action === DATA_HAS_ERROR) {
               return _.some($control, c => hasError($( c)));
            } else {
               $control.each(function(index) {
                    var $item = $(this);
                    $item = processErrorOnItem($item, message, action);
               }); 
               return $control;
            }
            
        }
        //function for set and clear error
        function processErrorOnItem($control: JQuery, message: string, action: string) {
            switch (action) {
                case 'set':
                    return setError($control, message);
                    
                case 'clear':
                    return clearErrors($control);                 
           }       
        }
        function setError($control: JQuery, message: string) {
            $control.data(DATA_HAS_ERROR, true);
            ui.errors.add({
                location: $control.data('name') || "",
                message: message,
                $control: $control
            });
            
            $control.parent().addClass('error');
            
            return $control;
        }
        
        function clearErrors($control: JQuery) {
            $control.data(DATA_HAS_ERROR, false);
            ui.errors.removeByElement($control);
            
            $control.parent().removeClass('error');
            
            return $control;
        }
        
        function hasError($control: JQuery) {
            return $control.data(DATA_HAS_ERROR) === true;
        }
    }
    
    module ntsPopup {
        let DATA_INSTANCE_NAME = 'nts-popup-panel';
        
        $.fn.ntsPopup = function () {
            
            if (arguments.length === 1) {
                var p: any = arguments[0];
                if (_.isPlainObject(p)) {
                    return init.apply(this, arguments);
                }
            }
            
            if (typeof arguments[0] === 'string') {
                return handleMethod.apply(this, arguments);
            }
        }
        
        function init(param): JQuery {
            var popup = new NtsPopupPanel($(this), param.position);
            var dismissible = param.dismissible === false;
            _.defer(function () {
                if(!dismissible) {
                    $(window).mousedown(function (e) {                  
                            //console.log(dismissible);
                            if ($(e.target).closest(popup.$panel).length === 0) {
                                popup.hide();
                            }                   
                    });
                }
            });
            
            return popup.$panel;
        }
        
        function handleMethod() {
            var methodName: string = arguments[0];
            var popup: NtsPopupPanel = $(this).data(DATA_INSTANCE_NAME);
            
            switch (methodName) {
                case 'show':
                    popup.show();
                    break;
                case 'hide':
                    popup.hide();
                    break;
                case 'destroy': 
                    popup.hide();
                    popup.destroy();
                    break;
                case 'toggle':
                    popup.toggle();
                    break;
            }
        }
        
        class NtsPopupPanel {
            
            $panel: JQuery;
            position: any;
            
            constructor($panel: JQuery, position: any) {
                
                this.position = position;
                
                this.$panel = $panel
                    .data(DATA_INSTANCE_NAME, this)
                    .addClass('popup-panel')
                    .appendTo('body');
            }
            
            show() {
                this.$panel
                    .css({
                        visibility: 'hidden',
                        display: 'block'
                    })
                    .position(this.position)
                    .css({
                        visibility: 'visible'
                    });
            }
            
            hide() {
                this.$panel.css({
                    display: 'none'
                });
            }
            
            destroy() {
               this.$panel = null; 
            }
            
            toggle() {
               var isDisplaying = this.$panel.css("display");
               if(isDisplaying === 'none') {
                  this.show(); 
               } else {
                  this.hide(); 
               }
            }
        }
    }
}