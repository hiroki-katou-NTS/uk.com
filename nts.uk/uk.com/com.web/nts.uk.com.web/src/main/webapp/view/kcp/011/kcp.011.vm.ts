module nts.uk.com.view.kcp011.share {
    const GET_WORKPLACE_URL = "bs/schedule/employeeinfo/workplacegroup/getAll";
    const SELECTED_MODE = {
        NONE: 0,
        FIRST: 1,
        ALL: 2
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
                if (self.setting().currentCodes) {
                    if (self.setting().multiple) {
                        let selecteds = _.filter(self.workplaceGroups(), (wkp) => {
                            let exist = _.filter(ids, (id) => { return id === wkp.id; });
                            return exist && exist.length > 0;
                        });

                        if (setting.currentCodes) {
                            setting.currentCodes(_.map(selecteds, (wkp) => { return wkp.code; }));
                        }
                        if (setting.currentNames) {
                            setting.currentNames(_.map(selecteds, (wkp) => { return wkp.name; }));
                        }
                    } else {
                        let selected = _.find(self.workplaceGroups(), (wkp) => {
                            return ids === wkp.id;
                        });
                        if (setting.currentCodes && selected) {
                            setting.currentCodes(selected.code);
                        }
                        if (setting.currentNames && selected) {
                            setting.currentNames(selected.name);
                        }
                    }
                }
            });

            if (setting.reloadData) {
                setting.reloadData.subscribe(() => {
                    self.loadData();
                });
            }

            if (setting.reloadComponent) {
                setting.reloadComponent.subscribe(() => {
                    self.reloadComponent(setting.reloadComponent());
                });
            }

            if (setting.isAlreadySetting && setting.alreadySettingList) {
                setting.alreadySettingList.subscribe((values) => {
                    let workplaceGs = self.workplaceGroups();
                    workplaceGs.forEach((workplace) => {
                        let isSetting = _.find(values, (settingId) => { return settingId == workplace.id });
                        workplace.configured = isSetting ? 1 : null;
                    });
                    self.workplaceGroups(workplaceGs);
                });
            }

            self.loadData().done(() => {
                let selectedMode = setting.selectedMode;
                if (selectedMode == SELECTED_MODE.NONE) {
                    setting.currentIds([]);
                } else if (selectedMode == SELECTED_MODE.FIRST) {
                    // show empty item will not select the empty one
                    let idx = setting.showEmptyItem ? 1 : 0
                    setting.currentIds(self.workplaceGroups()[idx] ? [self.workplaceGroups()[idx].id] : []);
                } else if (selectedMode == SELECTED_MODE.ALL) {
                    setting.currentIds(_.map(self.workplaceGroups(), (wkp) => { return wkp.id }));
                }
            });

        }

        loadData() {
            let self = this;
            let dfd = $.Deferred();

            nts.uk.ui.block.grayout();
            nts.uk.request.ajax("com", GET_WORKPLACE_URL).done((res) => {
                console.log(res);
                let workplaces = _.orderBy(res.workplaces, ['code'], ['asc']);
                if (self.setting().showEmptyItem) {
                    workplaces.unshift({ id: Math.random(), code: '', name: nts.uk.resource.getText("KCP011_5"), configured: null });
                }
                if (self.setting().isAlreadySetting && self.setting().alreadySettingList) {
                    workplaces.forEach((workplace) => {
                        let isSetting = _.find(self.setting().alreadySettingList(), (settingId) => { return settingId == workplace.id });
                        workplace.configured = isSetting ? 1 : null;
                    });
                }
                self.workplaceGroups(workplaces);
                if (self.setting().itemList) {
                    self.setting().itemList(workplaces);
                }
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
            return { codeWidth: codeWidth, nameWidth: nameWidth, alreadySettingWidth: alreadySettingWidth };
        }

        calculatePanelHeight() {
            let self = this;
            let height = self.setting().height ? (self.setting().height + 50) : 470;
            return height + 'px';
        }

        reloadComponent(config: Option) {
            $('#multi-list').ntsGridList(config);
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
        // 未選択表示
        showEmptyItem?: boolean;
        tabindex?: number;
        isResize?: boolean;
        rows?: number;
        isAlreadySetting?: any;
        alreadySettingList: any;
        // パネル有無
        showPanel?: boolean;
        reloadData: any;
        reloadComponent: any;
        // 表示行数
        height: any;
        // 選択モード // NONE = 0, FIRST = 1, ALL = 2
        selectedMode: number;
        itemList: any;
    }
}



ko.components.register('workplace-group', {
    viewModel: nts.uk.com.view.kcp011.share.WorkplaceGroupComponent, template: `
    <div id="workplace-group-pannel" data-bind="ntsPanel:{width: '380px', height: calculatePanelHeight(), direction: '', showIcon: true, visible: true}">
        <div class="" data-bind="css:{ 'caret-right caret-background': setting().showPanel }">
        <div data-bind="attr: {tabindex: setting().tabindex - 1},ntsSearchBox: {searchMode: 'filter',targetKey: 'id',comId: 'multi-list', 
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




