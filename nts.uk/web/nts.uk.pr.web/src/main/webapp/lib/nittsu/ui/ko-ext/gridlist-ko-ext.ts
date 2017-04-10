/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {

    /**
     * GridList binding handler
     */
    class NtsGridListBindingHandler implements KnockoutBindingHandler {

        init(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var HEADER_HEIGHT = 27;

            var $grid = $(element);

            if (nts.uk.util.isNullOrUndefined($grid.attr('id'))) {
                throw new Error('the element NtsGridList must have id attribute.');
            }

            var data = valueAccessor();
            var optionsValue: string = data.primaryKey !== undefined ? data.primaryKey : data.optionsValue;
            var options = ko.unwrap(data.dataSource !== undefined ? data.dataSource : data.options);
            var deleteOptions = ko.unwrap(data.deleteOptions);
            var observableColumns = ko.unwrap(data.columns);
            var iggridColumns = _.map(observableColumns, c => {
                c["key"] = c["key"] === undefined ? c["prop"] : c["key"];
                c["dataType"] = 'string';
                return c;
            });

            var features = [];
            features.push({ name: 'Selection', multipleSelection: data.multiple });
            features.push({ name: 'Sorting', type: 'local' });
            features.push({ name: 'RowSelectors', enableCheckBoxes: data.multiple, enableRowNumbering: true });

            $grid.igGrid({
                width: data.width,
                height: (data.height) + "px",
                primaryKey: optionsValue,
                columns: iggridColumns,
                virtualization: true,
                virtualizationMode: 'continuous',
                features: features
            });

            if (!util.isNullOrUndefined(deleteOptions) && !util.isNullOrUndefined(deleteOptions.deleteField)
                && deleteOptions.visible === true) {
                var sources = (data.dataSource !== undefined ? data.dataSource : data.options);
                $grid.ntsGridList("setupDeleteButton", {
                    deleteField: deleteOptions.deleteField,
                    sourceTarget: sources
                });
            }

            $grid.ntsGridList('setupSelecting');

            $grid.bind('selectionchanged', () => {
                if (data.multiple) {
                    let selected: Array<any> = $grid.ntsGridList('getSelected');
                    if (selected) {
                        data.value(_.map(selected, s => s.id));
                    } else {
                        data.value([]);
                    }
                } else {
                    let selected = $grid.ntsGridList('getSelected');
                    if (selected) {
                        data.value(selected.id);
                    } else {
                        data.value('');
                    }
                }

            });
            var gridId = $grid.attr('id');
            $grid.setupSearchScroll("igGrid", true);
        }

        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {

            var $grid = $(element);
            var data = valueAccessor();
            var optionsValue: string = data.primaryKey !== undefined ? data.primaryKey : data.optionsValue;
            var currentSource = $grid.igGrid('option', 'dataSource');
            var sources = (data.dataSource !== undefined ? data.dataSource() : data.options());
            if (!_.isEqual(currentSource, sources)) {
                let currentSources = sources.slice();
                var observableColumns = _.filter(ko.unwrap(data.columns), function(c){
                    c["key"] = c["key"] === undefined ? c["prop"] : c["key"];
                    return c["isDateColumn"] !== undefined && c["isDateColumn"] !== null && c["isDateColumn"] === true;
                });
                _.forEach(currentSources, function(s){
                    _.forEach(observableColumns, function(c){
                        let key = c["key"] === undefined ? c["prop"] : c["key"];
                        s[key] = moment(s[key]).format(c["format"]);
                    });        
//                    currentSources.push(s);
                });
                $grid.igGrid('option', 'dataSource', currentSources);
                $grid.igGrid("dataBind");
            }

            var currentSelectedItems = $grid.ntsGridList('getSelected');
            var isEqual = _.isEqualWith(currentSelectedItems, data.value(), function(current, newVal) {
                if ((current === undefined && newVal === undefined) || (current !== undefined && current.id === newVal)) {
                    return true;
                }
            })
            if (!isEqual) {
                $grid.ntsGridList('setSelected', data.value());
            }

            $grid.closest('.ui-iggrid').addClass('nts-gridlist').height(data.height);
        }
    }
    
    ko.bindingHandlers['ntsGridList'] = new NtsGridListBindingHandler();
}