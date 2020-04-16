module nts.uk.com.view.kcp011.share {
    const GET_WORKPLACE_URL = "at/schedule/employeeinfo/workplacegroup/getAll";
    const SELECTED_MODE = {
        NONE:0,
        FIRST:1,
        ALL:2
    }
    export class WorkplaceGroupComponent {

        workplaceGroups: KnockoutObservableArray<WorkplaceGroup> = ko.observableArray([]);
        columns: KnockoutObservableArray<any> = ko.observableArray([]);
        setting: KnockoutObservable<Option> = ko.observable({});
        constructor(params: Option) {
            let self = this;
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
            ]);

            let setting = self.setting();
            setting.currentIds.subscribe((ids) => {
                if(self.setting().currentCodes) {
                    let selecteds = _.filter(self.workplaceGroups(), (wkp) => {
                        return ids.indexOf(wkp.id) !== -1; 
                    });
                    if(setting.currentCodes) {
                        setting.currentCodes(_.map(selecteds, (wkp) => { return wkp.code; }));
                    }
                    if(setting.currentNames) {
                        setting.currentNames(_.map(selecteds, (wkp) => { return wkp.name; }));
                    }
                }
            });

            if(setting.reloadData) {
                setting.reloadData.subscribe(() => {
                    self.loadData();
                });
            }

            if(setting.isAlreadySetting && setting.alreadySettingList) {
                setting.alreadySettingList.subscribe((values) => {
                    let workplaceGs = self.workplaceGroups();
                    workplaceGs.forEach((workplace) => {
                        let isSetting = _.find(values, (settingId) => {return settingId == workplace.id });
                        workplace.configured = isSetting ? 1 : null;
                    });
                    self.workplaceGroups(workplaceGs);
                });
            }

            self.loadData().done(() => {
                let selectedMode = setting.selectedMode;
                if(selectedMode == SELECTED_MODE.NONE) {
                    setting.currentIds([]);
                } else if (selectedMode == SELECTED_MODE.FIRST) {
                    // show empty item will not select the empty one
                    let idx = setting.showEmptyItem ? 1 : 0
                    setting.currentIds(self.workplaceGroups()[idx] ? [self.workplaceGroups()[idx].id] : []);
                } else if (selectedMode == SELECTED_MODE.ALL) {
                    setting.currentIds(_.map(self.workplaceGroups(), (wkp) => { return wkp.id}));
                }
            });


        }
        
        loadData() {
            let self = this;
            let dfd = $.Deferred();
        
            nts.uk.ui.block.grayout();
            nts.uk.request.ajax( "com", GET_WORKPLACE_URL).done((res) => {
                console.log(res);
                let workplaces = _.orderBy(res.workplaces, ['code'], ['asc']);
                if(self.setting().showEmptyItem) {
                    workplaces.unshift({id:Math.random(), code: '', name: nts.uk.resource.getText("KCP011_5"), configured: null});
                }
                if(self.setting().isAlreadySetting && self.setting().alreadySettingList) {
                    workplaces.forEach((workplace) => {
                        let isSetting = _.find(self.setting().alreadySettingList(), (settingId) => {return settingId == workplace.id });
                        workplace.configured = isSetting ? 1 : null;
                    });
                }
                self.workplaceGroups(workplaces);
                dfd.resolve();
            }).always(() => {
                nts.uk.ui.block.clear();
            });

            return dfd.promise();
        }

        calculateWidth() {
            let self = this;
            let setting = self.setting();
            let codeWidth = setting.multiple ? 110 : 130;
            let nameWidth = setting.isAlreadySetting ? 150 : 230;
            let alreadySettingWidth = setting.isAlreadySetting ? 70 : 10;
            return {codeWidth: codeWidth, nameWidth: nameWidth, alreadySettingWidth: alreadySettingWidth};
        }

        calculatePanelHeight() {
            let self = this;
            let height = self.setting().height ? (self.setting().height + 50) : 470;
            if(!self.setting().showSearch) {
                height = height - 50;
            }
            return height + 'px';
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
        currentIds?: any;
        showEmptyItem?: boolean;
        tabindex?: number;
        isResize?: boolean;
        rows?: number;
        isAlreadySetting?: any;
        alreadySettingList: any;
        showSearch?: boolean;
        reloadData: any;
        height: any;
        selectedMode: number;
    }
}



ko.components.register('workplace-group', {
    viewModel: nts.uk.com.view.kcp011.share.WorkplaceGroupComponent, template: `
    <div id="workplace-group-pannel" data-bind="ntsPanel:{width: '380px', height: calculatePanelHeight(), direction: '', showIcon: true, visible: true}">
        <div class="caret-right caret-background">
        <div data-bind="visible: setting().showSearch ,attr: {tabindex: setting().tabindex - 1},ntsSearchBox: {searchMode: 'filter',targetKey: 'id',comId: 'multi-list', 
                  items: workplaceGroups, selectedKey: 'id', 
                  fields: ['id', 'code', 'name'], 
                  selected: setting().currentIds,
                  mode: 'igGrid'}" />
            <table id="multi-list"
                data-bind="attr: {tabindex: setting().tabindex}, 
                ntsGridList: {
                        height: setting().height ? setting().height: 420,
                        dataSource: workplaceGroups,
                        primaryKey: 'id',
                        columns: columns,
                        multiple: setting().multiple,
                        value: setting().currentIds,
                        rows: setting().rows,
                        columnResize: setting().isResize
                    }">
            </table>
        </div>
    </div
`});




