module nts.uk.at.view.kdl046.a.viewmodel {
    import setShare = nts.uk.ui.windows.setShared;
    import getShare = nts.uk.ui.windows.getShared;
    import flat = nts.uk.util.flatArray;

    export class ScreenModel {
        //
        modeName: KnockoutObservableArray<any>;
        target: KnockoutObservable<boolean> = ko.observable(nts.uk.ui.windows.getShared('dataShareDialog046').unit == 1 ? true : false);
        baseDate: KnockoutObservable<string> = ko.observable(nts.uk.ui.windows.getShared('dataShareDialog046').date);
        workplaceID: KnockoutObservable<string> = ko.observable(nts.uk.ui.windows.getShared('dataShareDialog046').workplaceId);
        workplaceGroupId: KnockoutObservable<string> = ko.observable(nts.uk.ui.windows.getShared('dataShareDialog046').workplaceGroupId);

        //KCP004
        treeGrid: TreeComponentOption;

        // KCP011
        kcp011Options: any;
        workplaceGroupList: KnockoutObservable<any> = ko.observable([]);
        currentIds: KnockoutObservable<any> = ko.observable(null);
        currentCodes: KnockoutObservable<any> = ko.observable([]);
        currentNames: KnockoutObservable<any> = ko.observable([]);

        constructor() {
            let self = this;

            self.modeName = ko.observableArray([
                { value: '0', name: nts.uk.resource.getText('Com_Workplace') },
                { value: '1', name: nts.uk.resource.getText('Com_WorkplaceGroup') }
            ]);

            //KCP004
            if (self.target() == false) {
                $('#gplG').hide();
                $('#gpl').show();
            }


            self.treeGrid = {
                isMultipleUse: false,
                isMultiSelect: false,
                treeType: 1,
                startMode: 0,
                selectedId: self.workplaceID,
                isDialog: true,
                baseDate: self.baseDate() == undefined ? ko.observable(new Date()) : self.baseDate,
                isShowSelectButton: false,
                selectType: self.workplaceID() == undefined ? 3 : 1,
                isDialog: true,
                maxRows: 10,
                tabindex: 1,
                systemType: 2
            };
            $('#tree-grid').ntsTreeComponent(self.treeGrid);


            // KCP011
            if (self.target() == true) {
                $('#gplG').show();
                $('#gpl').hide();
                if (self.currentCodes == []) {
                    nts.uk.ui.dialog.error({ messageId: "Msg_218" });
                }
            }
            self.kcp011Options = {
                itemList: self.workplaceGroupList,
                currentCodes: self.currentCodes,
                currentNames: self.currentNames,
                currentIds: self.workplaceGroupId,
                multiple: false,
                tabindex: 2,
                showPanel: false,
                isAlreadySetting: false,
                showEmptyItem: false,
                reloadData: ko.observable(''),
                height: 373,
                selectedMode: self.workplaceGroupId() ==  undefined ? 1 :3 
            };

        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let request = nts.uk.ui.windows.getShared('dataShareDialog046');

            let data = {
                baseDate: self.baseDate,
                workplaceID: self.workplaceID
            }
            self.target.subscribe((val) => {
                if (self.target() == false) {
                    $('#gplG').hide();
                    $('#gpl').show();

                }
                if (self.target() == true) {
                    $('#gplG').show();
                    $('#gpl').hide();
                }
            });

            let dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
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
                console.log(data);
                let listDataGrid = $('#tree-grid').getDataList();
                let flwps = flat(_.cloneDeep(listDataGrid), "children");
                let rowSelect = $('#tree-grid').getRowSelected();

                let item = null;
                if (rowSelect.length > 0) {
                    item = _.filter(flwps, function(o) { return o.code === rowSelect[0].code; });
                }
                if (self.target() == 1 && self.currentIds().length == 0) {
                    nts.uk.ui.dialog.error({ messageId: "Msg_218", messageParams: [nts.uk.resource.getText('Com_WorkplaceGroup')] });
                    return;
                }
                if (self.target() == 0 && self.workplaceID() === undefined) {
                    nts.uk.ui.dialog.error({ messageId: "Msg_218", messageParams: [nts.uk.resource.getText('Com_Workplace')] });
                    return;
                }
                if (self.target() == 1 && data.present == false) {
                    request.unit = 1;
                    request.workplaceGroupCode = self.currentCodes();
                    request.workplaceGroupID = self.currentIds();
                    request.workplaceGroupName = self.currentNames();
                    nts.uk.ui.windows.setShared('dataShareKDL046', request);
                    nts.uk.ui.windows.close();
                }
                if (self.target() == 1 && data.present == true) {
                    request.unit = 1;
                    request.workplaceGroupCode = data.workplaceGroupCode;
                    request.workplaceGroupID = data.workplaceGroupID;
                    request.workplaceGroupName = data.workplaceGroupName;
                    nts.uk.ui.windows.setShared('dataShareKDL046', request);
                    nts.uk.ui.windows.close();


                }
                if (self.target() == 0 && data.present == true) {
                    if (!_.isNil(item)) {
                        request.unit = 0;
                        request.workplaceCode = item[0].code;
                        request.workplaceId = item[0].id;
                        request.workplaceName = item[0].name;
                        nts.uk.ui.dialog.confirmDanger({ messageId: "Msg_1769", messageParams: [data.workplaceGroupCode, data.workplaceGroupName] }).ifYes(() => {
                            nts.uk.ui.windows.setShared('dataShareKDL046', request);
                            nts.uk.ui.windows.close();
                        }).ifNo(() => {

                        });

                    }
                }
                if (self.target() == 0 && data.present == false) {
                    if (!_.isNil(item)) {
                        request.unit = 0;
                        request.workplaceCode = item[0].code;
                        request.workplaceId = item[0].id;
                        request.workplaceName = item[0].name;
                    }
                    nts.uk.ui.windows.setShared('dataShareKDL046', request);
                    nts.uk.ui.windows.close();
                }
                console.log(request);

            });
            //nts.uk.ui.windows.close();
        }

    }
}