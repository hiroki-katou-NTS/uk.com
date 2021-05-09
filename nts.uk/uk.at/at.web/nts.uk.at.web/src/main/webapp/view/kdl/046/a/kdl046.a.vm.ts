module nts.uk.at.view.kdl046.a.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog;
    import flat = nts.uk.util.flatArray;

    export class ScreenModel {
        target: KnockoutObservable<number>;
        baseDate: string;
        selectType: number;
        isMultiple: boolean;
        multipleUsage: boolean;
        workplaceId: KnockoutObservable<string> | KnockoutObservableArray<string>;
        workplaceGroupId: KnockoutObservable<string> | KnockoutObservableArray<string>;

        constructor() {
            let self = this;
            const data = getShared('dataShareDialog046');
            self.target = ko.observable(parseInt(data.unit));
            self.baseDate = _.isEmpty(data.baseDate) ? (_.isEmpty(data.date) ? new Date().toISOString() : data.date) : data.baseDate;
            self.isMultiple = !!data.isMultiple;
            self.multipleUsage = !data.showBaseDate;
            self.workplaceId = self.isMultiple ? ko.observableArray(data.workplaceId || []) : ko.observable(data.workplaceId);
            self.workplaceGroupId = self.isMultiple ? ko.observableArray(data.workplaceGroupId || []) : ko.observable(data.workplaceGroupId);
            if (self.target() == 0) {
                self.selectType = _.isEmpty(self.workplaceId()) ? 3 : 1;
            } else {
                self.selectType = _.isEmpty(self.workplaceGroupId()) ? 3 : 1;
            }
        }

        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }

        submit() {
            //Workplace 0 : Workplace Group 1
            let self = this;
            if (self.target() == 1 && _.isEmpty(self.workplaceGroupId())) {
                dialog.error({ messageId: "Msg_218", messageParams: [getText('Com_WorkplaceGroup')] });
                return;
            }
            if (self.target() == 0 && _.isEmpty(self.workplaceId())) {
                dialog.error({ messageId: "Msg_218", messageParams: [getText('Com_Workplace')] });
                return;
            }
            let result: any = {};
            if (self.target() == 1) {
                result.unit = 1;
                const workplaceGroups: Array<any> = ko.dataFor($("#workplace-group-pannel")[0]).workplaceGroups();
                if (self.isMultiple) {
                    let items = _.filter(workplaceGroups, (o) => self.workplaceGroupId().indexOf(o.id) >= 0);
                    result.workplaceGroups = items;
                } else {
                    let itemWplGr = _.filter(workplaceGroups, (o) => o.id === self.workplaceGroupId());
                    result.workplaceGroupCode = itemWplGr.length > 0 ? itemWplGr[0].code : '';
                    result.workplaceGroupID = itemWplGr.length > 0 ? itemWplGr[0].id : '';
                    result.workplaceGroupName = itemWplGr.length > 0 ? itemWplGr[0].name : '';
                }
                setShared('dataShareKDL046', result);
                nts.uk.ui.windows.close();
            } else {
                service.getData(self.isMultiple ? self.workplaceId() : [self.workplaceId()]).done(function(data: any) {
                    const listDataGrid = $('#workplace-tree-grid').getDataList();
                    const flwps = flat(_.cloneDeep(listDataGrid), "children");

                    if (self.isMultiple) {
                        // WIP
                        // if (_.isEmpty(data)) {
                        //
                        // }
                        // for (const wkpId in data) {
                        //
                        // }
                        result.unit = 0;
                        const items = _.filter(flwps, (o: any) => self.workplaceId().indexOf(o.id) >= 0);
                        result.workplaces = items.map(i => ({id: i.id, code: i.code, name: i.name}));
                        nts.uk.ui.windows.setShared('dataShareKDL046', result);
                        nts.uk.ui.windows.close();
                    } else {
                        const wkpGroup = data[self.workplaceId().toString()];
                        if (wkpGroup) {
                            result.unit = 1;
                            result.workplaceGroupCode = wkpGroup.workplaceGroupCode;
                            result.workplaceGroupID = wkpGroup.workplaceGroupID;
                            result.workplaceGroupName = wkpGroup.workplaceGroupName;
                            nts.uk.ui.dialog.confirmDanger({ messageId: "Msg_1769", messageParams: [wkpGroup.workplaceGroupCode, wkpGroup.workplaceGroupName] }).ifYes(() => {
                                nts.uk.ui.windows.setShared('dataShareKDL046', result);
                                nts.uk.ui.windows.close();
                            }).ifNo(() => {

                            });
                        } else {
                            const item = _.find(flwps, (o: any) => o.id == self.workplaceId());
                            if (!_.isNil(item)) {
                                result.unit = 0;
                                result.workplaceCode = item.code;
                                result.workplaceId = item.id;
                                result.workplaceName = item.workplaceDisplayName;
                            }
                            nts.uk.ui.windows.setShared('dataShareKDL046', result);
                            nts.uk.ui.windows.close();
                        }
                    }
                }).fail(error => {
                    dialog.alert(error);
                });
            }
        }
    }
}