/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {
    
    class NtsLegentButtonBindingHandler implements KnockoutBindingHandler {
        
        /**
         * Init. 
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            let data = valueAccessor();
            let $container = $(element);
            
            $container.text('■ 凡例');
            
            $container.click(() => {
                showLegendPanel($container, data);
            });
        }
        
        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
        }
    }
    
    interface LegendOptions {
        items: LegendItem[];
    }
    
    interface LegendItem {
        cssClass: LegendCssClass;
        colorCode: String;
        labelText: String;
        
    }
    
    interface LegendCssClass {
        className: String;
        colorPropertyName: String;
    }
    
    function getColorCodeFromItem(legendItem: LegendItem) {
        return util.optional.of(legendItem.cssClass)
            .map(cc => getColorCodeFromCssClass(cc))
            .orElse(legendItem.colorCode);
    }

    function getColorCodeFromCssClass(legendCssClass: LegendCssClass) {
        let $temp = $('<span/>').addClass(legendCssClass.className)
            .hide()
            .appendTo('body');
        let colorCode = $temp.css(legendCssClass.colorPropertyName);
        $temp.remove();
        return colorCode;
    }
    
    function showLegendPanel($legendButton: any, options: LegendOptions) {
        
        let legendSize = 18;
        
        let $panel = $('<div/>').addClass('nts-legendbutton-panel');
        
        options.items.forEach(item => {
            $('<div/>').addClass('legend-item')
                .append($('<div/>')
                    .addClass('legend-item-symbol')
                    .css({
                        'background-color': getColorCodeFromItem(item),
                        width: legendSize + 'px',
                        height: legendSize + 'px'
                    })
                    .text('　'))
                .append($('<div/>')
                    .addClass('legend-item-label')
                    .text(item.labelText))
                .appendTo($panel);
        });
        
        $panel.appendTo('body').position({
            my: 'left top',
            at: 'left bottom',
            of: $legendButton
        });
        
        _.defer(() => {
            $(window).bind('mousedown.legendpanel', () => {
                $panel.remove();
                $(window).unbind('mousedown.legendpanel');
            });
        });
    }

    ko.bindingHandlers['ntsLegendButton'] = new NtsLegentButtonBindingHandler();
}