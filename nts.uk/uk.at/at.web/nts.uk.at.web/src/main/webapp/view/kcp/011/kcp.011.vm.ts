module nts.uk.at.view.kcp011.share {
    
    export class WorkplaceGroupComponent {

        workplaceGroups: KnockoutObservableArray<WorkplaceGroup> = ko.observableArray([]);
        columns: KnockoutObservableArray<any> = ko.observableArray([]);
        setting: KnockoutObservable<Option> = ko.observable({});
        constructor(params: Option) {
            let self = this;
            console.log(params);
            self.setting(params.options);
            let widthCal = self.calculateWidth();
            self.columns([
                { headerText: '', prop: 'id', width: 100, hidden: true },
                { headerText: nts.uk.resource.getText("KCP011_2"), prop: 'code', width: widthCal.codeWidth },
                { headerText: nts.uk.resource.getText("KCP011_3"), prop: 'name', width: widthCal.nameWidth, formatter: _.escape },
                {
                    headerText: nts.uk.resource.getText("KCP011_4"), prop: 'configured', width: widthCal.alreadySettingWidth, hidden: !self.setting().isAlreadySetting,
                    template: '{{if ${configured} == 1 }}<div class="cssDiv"><i  class="icon icon icon-78 cssI"></i></div>{{/if}}'
                }
            ])
            self.loadData();
        }

        loadData() {
            let self = this;
            self.workplaceGroups([
                {id:1, code:'a', name:'testtt', configured: 1},
                {id:2, code:'b', name:'testtt', configured: 0},
                {id:3, code:'c', name:'testtt', configured: 1},
                {id:4, code:'d', name:'testtt', configured: 0},
                {id:5, code:'e', name:'testtt', configured: 1}
            ]);
        }

        calculateWidth() {
            let self = this;
            let setting = self.setting();
            let codeWidth = setting.multiple ? 80 : 100;
            let nameWidth = setting.isAlreadySetting ? 150 : 220;
            let alreadySettingWidth = setting.isAlreadySetting ? 70 : 10;
            return {codeWidth: codeWidth, nameWidth: nameWidth, alreadySettingWidth: alreadySettingWidth};
        }


       
    }

    export interface WorkplaceGroup {
        id: string;
        code: string;
        name: string;
    }
    
    export interface Option {
        multiple?: boolean;
        currentCodes?: any;
        showEmptyItem?: boolean;
        tabindex?: number;
        isResize?: boolean;
        rows?: number;
        isAlreadySetting?: any;
        showSearch?: boolean;
    }
}



ko.components.register('workplace-group', {
    viewModel: nts.uk.at.view.kcp011.share.WorkplaceGroupComponent, template: `
    <div id="workplace-group-pannel" data-bind="ntsPanel:{width: '350px', height: '370px', direction: '', showIcon: true, visible: true}">
        <div class="caret-right caret-background">
        <div data-bind="visible: setting().showSearch ,attr: {tabindex: setting().tabindex - 1},ntsSearchBox: {searchMode: 'filter',targetKey: 'id',comId: 'multi-list', 
                  items: workplaceGroups, selectedKey: 'id', 
                  fields: ['id', 'code', 'name'], 
                  selected: setting().currentCodes,
                  mode: 'igGrid'}" />
            <table id="multi-list"
                data-bind="attr: {tabindex: setting().tabindex}, 
                ntsGridList: {
                        height: 320,
                        dataSource: workplaceGroups,
                        primaryKey: 'id',
                        columns: columns,
                        multiple: setting().multiple,
                        value: setting().currentCodes,
                        rows: setting().rows,
                        columnResize: setting().isResize
                    }">
            </table>
        </div>
    </div
`});




