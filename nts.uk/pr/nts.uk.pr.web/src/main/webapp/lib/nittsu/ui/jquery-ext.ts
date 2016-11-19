interface JQuery {
    ntsPopup(args: any);
}

module nts.uk.ui.jqueryExtentions {

    module ntsTextBox {
        $.fn.ntsTextBox = function () {

            if (arguments.length === 1) {
                var p: any = arguments[0];
                if (_.isPlainObject(p)) {
                    return init(p);
                }
            }
        };

        function init(param): JQuery {
            return null;
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
            
            _.defer(function () {
                $(window).mousedown(function (e) {
                    if ($(e.target).closest(popup.$panel).length === 0) {
                        popup.hide();
                    }
                });
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
        }
    }
}