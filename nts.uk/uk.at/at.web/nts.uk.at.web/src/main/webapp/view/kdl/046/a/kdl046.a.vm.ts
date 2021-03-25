module nts.uk.at.view.kdl046.a.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog;
    import flat = nts.uk.util.flatArray;

    export class ScreenModel {
        target: KnockoutObservable<number>;
        baseDate: KnockoutObservable<string>;
        workplaceID: KnockoutObservable<string>;
        workplaceGroupId: KnockoutObservable<string>;

        constructor() {
            let self = this;
            const data = getShared('dataShareDialog046');
            self.target = ko.observable(parseInt(data.unit));
            self.baseDate = ko.observable(data.date);
            self.workplaceID = ko.observable(data.workplaceId);
            self.workplaceGroupId = ko.observable(data.workplaceGroupId);
        }

        cancel_Dialog(): any {
            let self = this;
            nts.uk.ui.windows.close();
        }

        submit() {
            //Workplace 0 : Workplace Group 1
            let self = this;
            let request: any = {};
            service.getData(self.workplaceID()).done(function(data: any) {
                if (self.target() == 1 && _.isNil(self.workplaceGroupId())) {
                    dialog.error({ messageId: "Msg_218", messageParams: [getText('Com_WorkplaceGroup')] });
                    return;
                }
                if (self.target() == 0 && _.isNil(self.workplaceID())) {
                    dialog.error({ messageId: "Msg_218", messageParams: [getText('Com_Workplace')] });
                    return;
                }
                if (self.target() == 1) {
                    const workplaceGroups: Array<any> = ko.dataFor($("#workplace-group-pannel")[0]).workplaceGroups();
                    if (data.present) {
                        let itemWplGr = _.filter(workplaceGroups, function(o) { return o.id === self.workplaceGroupId() });
                        request.unit = 1;
                        request.workplaceGroupCode = itemWplGr.length > 0 ? itemWplGr[0].code : '';
                        request.workplaceGroupID = itemWplGr.length > 0 ? itemWplGr[0].id : '';
                        request.workplaceGroupName = itemWplGr.length > 0 ? itemWplGr[0].name : '';
                    } else {
                        let itemWplGr = _.filter(workplaceGroups, function(o) { return o.id === self.workplaceGroupId() });
                        request.unit = 1;
                        request.workplaceGroupCode = itemWplGr.length > 0 ? itemWplGr[0].code : '';
                        request.workplaceGroupID = itemWplGr.length > 0 ? itemWplGr[0].id : '';
                        request.workplaceGroupName = itemWplGr.length > 0 ? itemWplGr[0].name : '';
                    }
                    setShared('dataShareKDL046', request);
                    nts.uk.ui.windows.close();
                } else {
                    const listDataGrid = $('#workplace-tree-grid').getDataList();
                    const flwps = flat(_.cloneDeep(listDataGrid), "children");
                    const item = _.find(flwps, (o: any) => o.id == self.workplaceID());

                    if (data.present) {
                        if (!_.isNil(item)) {
                            request.unit = 1;
                            request.workplaceGroupCode = data.workplaceGroupCode;
                            request.workplaceGroupID = data.workplaceGroupID;
                            request.workplaceGroupName = data.workplaceGroupName;
                            nts.uk.ui.dialog.confirmDanger({ messageId: "Msg_1769", messageParams: [data.workplaceGroupCode, data.workplaceGroupName] }).ifYes(() => {
                                nts.uk.ui.windows.setShared('dataShareKDL046', request);
                                nts.uk.ui.windows.close();
                            }).ifNo(() => {

                            });

                        }
                    } else {
                        if (!_.isNil(item)) {
                            request.unit = 0;
                            request.workplaceCode = item.code;
                            request.workplaceId = item.id;
                            request.workplaceName = item.workplaceDisplayName;
                        }
                        nts.uk.ui.windows.setShared('dataShareKDL046', request);
                        nts.uk.ui.windows.close();
                    }
                }
            });
        }

    }
}