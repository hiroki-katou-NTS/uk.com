module nts.uk.at.view.kmk009.a.viewmodel {

    import EnumUse = service.model.Enum;
    import Enum = service.model.Enum;
    import WorkTypeDto = service.model.WorkTypeDto;
    import WorkTimeDto = service.model.WorkTimeDto;

    export class ScreenModel {
        itemTotalTimes: KnockoutObservableArray<model.TotalTimes>;
        itemTotalTimesDetail: KnockoutObservable<model.TotalTimesDetail>;
        totalClsEnums: Array<Enum>;
        totalClsEnumsUse: Array<EnumUse>;
        valueEnum: KnockoutObservable<number>;
        currentCode: KnockoutObservable<any>;
        columns: KnockoutObservableArray<any>;
        useSet: KnockoutObservableArray<any>;
        selectUse: KnockoutObservable<any>;
        enableUse: KnockoutObservable<boolean>;
        enableUpper: KnockoutObservable<boolean>;
        selectUppper: KnockoutObservable<any>;
        selectUnder: KnockoutObservable<any>;
        enableUnder: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.itemTotalTimes = ko.observableArray([]);
            self.itemTotalTimesDetail = ko.observable(null);
            self.totalClsEnums = [];
            self.totalClsEnumsUse = [];
            self.valueEnum = ko.observable(null);
            self.currentCode = ko.observable(null);
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText('KMK009_4'), key: 'totalCountNo', formatter: _.escape, width: 50 },
                { headerText: nts.uk.resource.getText('KMK009_5'), key: 'useAtrName', formatter: _.escape, width: 80 },
                { headerText: nts.uk.resource.getText('KMK009_6'), key: 'totalTimesName', formatter: _.escape, width: 150 },
                { headerText: nts.uk.resource.getText('KMK009_14'), key: 'summaryAtrName', formatter: _.escape, width: 100 }
            ]);
            self.useSet = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText("KMK009_12") },
                { code: '0', name: nts.uk.resource.getText("KMK009_13") },
            ]);
            self.selectUse = ko.observable(0);
            self.selectUppper = ko.observable(0);
            self.selectUnder = ko.observable(0);
            self.enableUse = ko.observable(false);
            self.enableUpper = ko.observable(false);
            self.enableUnder = ko.observable(false);


            //subscribe currentCode
            self.currentCode.subscribe(function(codeChanged) {
                self.clearError();
                if (codeChanged == 0) { return; }
                self.selectUse(null);
                self.loadAllTotalTimesDetail(codeChanged);
            });
            //subscribe selectUse
            self.selectUse.subscribe(function(codeChanged) {
                if (codeChanged == 1) {
                    self.enableUse(true);
                    self.itemTotalTimesDetail().useAtr(1);
                } else {
                    self.enableUse(false);
                    self.itemTotalTimesDetail().useAtr(0);
                }
            });

            //subscribe upper Limit
            self.selectUppper.subscribe(function(codeChanged) {
                if ((codeChanged == true && self.selectUse() === "1") || (codeChanged == true && self.selectUse() === 1)) {
                    self.enableUpper(true);
                    self.itemTotalTimesDetail().totalCondition().upperLimitSettingAtr(1);
                } else {
                    self.enableUpper(false);
                    self.itemTotalTimesDetail().totalCondition().upperLimitSettingAtr(0);
                }
            });

            //subscribe under Limit
            self.selectUnder.subscribe(function(codeChanged) {
                if ((codeChanged == true && self.selectUse() === "1") || (codeChanged == true && self.selectUse() === 1)) {
                    self.enableUnder(true);
                    self.itemTotalTimesDetail().totalCondition().lowerLimitSettingAtr(1);
                } else {
                    self.enableUnder(false);
                    self.itemTotalTimesDetail().totalCondition().lowerLimitSettingAtr(0);
                }
            });

        }
        /**
         * start page
         * get all divergence time
         * get all divergence name
         */
        public startPage(): JQueryPromise<any> {
            var self = this;
            //            nts.uk.ui.block.invisible();
            var dfd = $.Deferred();
            self.loadAllTotalTimesDetail(1);

            self.loadTotalClsEnum().done(function() {
                if (self.totalClsEnums.length > 0) {
                    self.valueEnum(self.totalClsEnums[self.itemTotalTimesDetail().summaryAtr()].value);
                }
                self.loadTotalUseEnum().done(function() {
                    self.loadAllTotalTimes().done(() => {
                        dfd.resolve();
                    });
                });

            });



            return dfd.promise();
        }

        private loadAllTotalTimes(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();

            nts.uk.ui.block.grayout();


            service.getAllTotalTimes().done(function(lstTotalTimes: Array<model.TotalTimes>) {
                nts.uk.ui.block.clear();
                self.itemTotalTimes([]);
                self.itemTotalTimes(lstTotalTimes);

                for (var i = 0; i < self.itemTotalTimes().length; i++) {
                    self.itemTotalTimes()[i].summaryAtrName = self.totalClsEnums[self.itemTotalTimes()[i].summaryAtr].localizedName;
                    self.itemTotalTimes()[i].useAtrName = self.totalClsEnumsUse[self.itemTotalTimes()[i].useAtr].localizedName;
                }
                self.itemTotalTimes.valueHasMutated();


                //                    let rdivTimeFirst = _.first(lstDivTime);
                //                    self.currentCode(rdivTimeFirst.divTimeId);
                //                }
                dfd.resolve();
            })

            return dfd.promise();
        }

        // loadAllTotalTimesDetail
        private loadAllTotalTimesDetail(codeChanged: number): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();

            nts.uk.ui.block.grayout();

            service.getAllTotalTimesDetail(codeChanged).done(function(item: model.TotalTimesDetail) {
                nts.uk.ui.block.clear();
                if (item == null || item === undefined) {
                    self.itemTotalTimesDetail(null);
                } else {
                    // check errors
                    self.itemTotalTimesDetail(new model.TotalTimesDetail(item.totalCountNo, item.countAtr, item.useAtr, item.totalTimesName,
                        item.totalTimesABName, item.summaryAtr, item.totalCondition, item.listTotalSubjects));
                    self.selectUse(self.itemTotalTimesDetail().useAtr());

                    // disable or enable Upper limit and under linit
                    self.selectUppper(self.itemTotalTimesDetail().totalCondition().upperLimitSettingAtr());
                    if (self.selectUppper() === 1) {
                        self.enableUpper(true);
                    } else {
                        self.enableUpper(false);
                    }
                    self.selectUnder(self.itemTotalTimesDetail().totalCondition().lowerLimitSettingAtr());
                    if (self.selectUnder() === 1) {
                        self.enableUnder(true);
                    } else {
                        self.enableUnder(false);
                    }
                    self.loadListWorkType();
                    self.loadListWorkTimes();
                }
                // load all data  Enum
                self.loadTotalClsEnum().done(function() {
                    if (self.totalClsEnums.length > 0) {
                        self.valueEnum(self.totalClsEnums[self.itemTotalTimesDetail().summaryAtr()].value);
                    }
                    dfd.resolve();
                });

            });


            return dfd.promise();
        }

        // loadListWorkTypes
        private loadListWorkType(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();

            nts.uk.ui.block.grayout();

            let lstWorkTypeCd: Array<string> = _.filter(self.itemTotalTimesDetail().listTotalSubjects(), (item) => item.workTypeAtr() == 0)
                .map((item) => item.workTypeCode());

            service.findListByIdWorkTypes(lstWorkTypeCd).done(function(res: Array<WorkTypeDto>) {
                nts.uk.ui.block.clear();

                if (res) {
                    self.itemTotalTimesDetail().workTypeInfo(res.map(item => item.workTypeCode + ' ' + item.name).join("＋"));
                } else {
                    self.itemTotalTimesDetail().workTypeInfo('');
                }
            });

            return dfd.promise();
        }


        // loadListWorkTimes
        private loadListWorkTimes(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();

            nts.uk.ui.block.grayout();

            let lstWorkTypeCd: Array<string> = _.filter(self.itemTotalTimesDetail().listTotalSubjects(), (item) => item.workTypeAtr() == 1)
                .map((item) => item.workTypeCode());

            service.findListByIdWorkTimes(lstWorkTypeCd).done(function(res: Array<WorkTimeDto>) {
                nts.uk.ui.block.clear();

                if (res) {
                    self.itemTotalTimesDetail().workingInfo(res.map(item => item.code + ' ' + item.name).join("＋"));
                } else {
                    self.itemTotalTimesDetail().workingInfo('');
                }
            });

            return dfd.promise();
        }

        // load enum
        private loadTotalClsEnum(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();

            nts.uk.ui.block.grayout();

            // get setting
            service.getTotalClsEnum().done(function(dataRes: Array<Enum>) {

                self.totalClsEnums = dataRes;

                nts.uk.ui.block.clear();

                dfd.resolve();
            });

            return dfd.promise();
        }

        // load enum enum
        private loadTotalUseEnum(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();

            nts.uk.ui.block.grayout();

            // get setting
            service.getTotalUseEnum().done(function(dataRes: Array<EnumUse>) {

                self.totalClsEnumsUse = dataRes;

                nts.uk.ui.block.clear();

                dfd.resolve();
            });

            return dfd.promise();
        }



        // save Daily Pattern in database
        public save() {
            let self = this;
            nts.uk.ui.block.grayout();
            //trim() name
            self.itemTotalTimesDetail().totalTimesName($.trim(self.itemTotalTimesDetail().totalTimesName()));
            self.itemTotalTimesDetail().totalTimesABName($.trim(self.itemTotalTimesDetail().totalTimesABName()));
            // save enum
            self.itemTotalTimesDetail().summaryAtr(self.valueEnum());
            // define dataDto
            var detailDto = self.itemTotalTimesDetail();

            let command: any = {};

            command.totalCountNo = detailDto.totalCountNo;
            command.countAtr = detailDto.countAtr();
            command.useAtr = detailDto.useAtr();
            command.totalTimesName = detailDto.totalTimesName();
            command.totalTimesABName = detailDto.totalTimesABName();
            command.summaryAtr = detailDto.summaryAtr();

            /** The total condition. */
            let totalCondition: any = {};
            totalCondition.upperLimitSettingAtr = detailDto.totalCondition().upperLimitSettingAtr();
            totalCondition.lowerLimitSettingAtr = detailDto.totalCondition().lowerLimitSettingAtr();
            totalCondition.thresoldUpperLimit = detailDto.totalCondition().thresoldUpperLimit();
            totalCondition.thresoldLowerLimit = detailDto.totalCondition().thresoldLowerLimit();

            command.totalCondition = totalCondition;

            let listTotalSubjects: Array<any> = [];

            for (let totalObj of detailDto.listTotalSubjects()) {
                listTotalSubjects.push({ workTypeCode: totalObj.workTypeCode(), workTypeAtr: totalObj.workTypeAtr() });
            }
            command.listTotalSubjects = listTotalSubjects;
            // call service save all 
            service.saveAllTotalTimes(command).done(function() {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                    self.loadAllTotalTimes().done(function() {
                        //                        self.loadAllTotalTimesDetail(self.currentCode()).done(function() {
                        //                        });
                    });
                });


            }).fail(function(res) {
                alert(res.message);
            }).always(function() {
                nts.uk.ui.block.clear();
            });
        }
        // openKDL001Dialog
        public openKDL001Dialog() {
            var self = this;
            nts.uk.ui.block.grayout();
            // check worktype or worktime send to KDL001Dialog
            var listWorkType = [];
            var listWorkCode = [];
            for (let i = 0; i < self.itemTotalTimesDetail().listTotalSubjects().length; i++) {
                if (self.itemTotalTimesDetail().listTotalSubjects()[i].workTypeAtr() === 0) {
                    listWorkType[i] = self.itemTotalTimesDetail().listTotalSubjects()[i].workTypeCode();
                } else {
                    listWorkCode[i] = self.itemTotalTimesDetail().listTotalSubjects()[i].workTypeCode();
                }
            }
            service.findAllWorkTimes().done(function(dataRes: Array<WorkTimeDto>) {
                //list All workTime
                let list: Array<string> = dataRes.map(item => item.code);

                nts.uk.ui.windows.setShared('kml001multiSelectMode', true);
                nts.uk.ui.windows.setShared('kml001selectAbleCodeList', list);
                nts.uk.ui.windows.setShared('kml001selectedCodeList', listWorkCode, true);
                nts.uk.ui.windows.sub.modal('/view/kdl/001/a/index.xhtml', { title: '選択肢の設定', }).onClosed(function(): any {
                    nts.uk.ui.block.clear();
                    console.log(nts.uk.ui.windows.getShared('kml001selectedCodeList'));
                    var shareWorkCocde: Array<string> = nts.uk.ui.windows.getShared('kml001selectedCodeList');
                    // deleted data worktype

                    self.itemTotalTimesDetail().listTotalSubjects(_.filter(self.itemTotalTimesDetail().listTotalSubjects(), (item) => item.workTypeAtr() == 0));
                    // insert data worktype
                    for (let i = 0; i < shareWorkCocde.length; i++) {
                        self.itemTotalTimesDetail().listTotalSubjects().push(new model.TotalSubjects(shareWorkCocde[i], 1));
                    }
                    self.loadListWorkType();
                    self.loadListWorkTimes();
                    if ($('#inpDialog').ntsError("hasError") == true) {
                        $('#inpDialog').ntsError('clear');
                    }
                    $("#itemname").focus();
                });
            });
        }
        //        open KDL002Dialog()
        public openKDL002Dialog() {
            var self = this;
            nts.uk.ui.block.grayout();
            // check worktype or worktime send to KDL002Dialog
            var listWorkType = [];
            var listWorkCode = [];
            for (let i = 0; i < self.itemTotalTimesDetail().listTotalSubjects().length; i++) {
                if (self.itemTotalTimesDetail().listTotalSubjects()[i].workTypeAtr() === 0) {
                    listWorkType[i] = self.itemTotalTimesDetail().listTotalSubjects()[i].workTypeCode();
                } else {
                    listWorkCode[i] = self.itemTotalTimesDetail().listTotalSubjects()[i].workTypeCode();
                }
            }

            service.findAllWorkTypes().done(function(dataRes: Array<WorkTypeDto>) {
                //list All workType
                let list: Array<string> = dataRes.map(item => item.workTypeCode);
                nts.uk.ui.windows.setShared('KDL002_Multiple', true);
                nts.uk.ui.windows.setShared('KDL002_AllItemObj', list);
                nts.uk.ui.windows.setShared('KDL002_SelectedItemId', listWorkType, true);
                nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml', { title: '選択肢の設定', }).onClosed(function(): any {
                    nts.uk.ui.block.clear();
                    console.log(nts.uk.ui.windows.getShared('KDL002_SelectedNewItem'));
                    var shareWorkType: Array<any> = nts.uk.ui.windows.getShared('KDL002_SelectedNewItem');
                    // deleted data worktype
                    self.itemTotalTimesDetail().listTotalSubjects(_.filter(self.itemTotalTimesDetail().listTotalSubjects(), (item) => item.workTypeAtr() == 1));
                    // insert data worktype
                    for (let i = 0; i < shareWorkType.length; i++) {
                        self.itemTotalTimesDetail().listTotalSubjects().push(new model.TotalSubjects(shareWorkType[i].code, 0));
                    }
                    self.loadListWorkType();
                    self.loadListWorkTimes();
                    if ($('#inpDialog').ntsError("hasError") == true) {
                        $('#inpDialog').ntsError('clear');
                    }
                    $("#itemname").focus();
                });
            });
        }

        clearError(): void {
            if ($('.nts-validate').ntsError("hasError") == true) {
                $('.nts-validate').ntsError('clear');
            }
            if ($('.nts-editor').ntsError("hasError") == true) {
                $('.nts-input').ntsError('clear');
            }
        }

    }




    export module model {
        export class TotalTimes {
            totalCountNo: number;
            summaryAtr: number;
            useAtr: number;
            totalTimesName: KnockoutObservable<string>;
            summaryAtrName: string;
            useAtrName: string
            constructor(totalCountNo: number, summaryAtr: number, useAtr: number, totalTimesName: string) {
                this.totalCountNo = totalCountNo;
                this.summaryAtr = summaryAtr;
                this.useAtr = useAtr;
                this.totalTimesName = ko.observable(totalTimesName);
                this.summaryAtrName = null;
                this.useAtrName = null;
            }
        }

        export class TotalTimesDetail {
            totalCountNo: number;
            countAtr: KnockoutObservable<number>;
            useAtr: KnockoutObservable<number>;
            totalTimesName: KnockoutObservable<string>;
            totalTimesABName: KnockoutObservable<string>;
            summaryAtr: KnockoutObservable<number>;
            totalCondition: KnockoutObservable<model.TotalCondition>;
            listTotalSubjects: KnockoutObservableArray<model.TotalSubjects>;
            workTypeInfo: KnockoutObservable<string>;
            workingInfo: KnockoutObservable<string>;


            constructor(totalCountNo: number, countAtr: number, useAtr: number, totalTimesName: string, totalTimesABName: string, summaryAtr: number, totalCondition: model.TotalCondition, listTotalSubjects: Array<model.TotalSubjects>) {
                this.totalCountNo = totalCountNo;
                this.countAtr = ko.observable(countAtr);
                this.useAtr = ko.observable(useAtr);
                this.totalTimesName = ko.observable(totalTimesName);
                this.totalTimesABName = ko.observable(totalTimesABName);
                this.summaryAtr = ko.observable(summaryAtr);
                this.totalCondition = ko.observable(new model.TotalCondition(totalCondition.upperLimitSettingAtr, totalCondition.lowerLimitSettingAtr,
                    totalCondition.thresoldUpperLimit, totalCondition.thresoldLowerLimit));
                this.listTotalSubjects = ko.observableArray([]);
                for (let i = 0; i < listTotalSubjects.length; i++) {
                    // add list
                    this.listTotalSubjects.push(new model.TotalSubjects(listTotalSubjects[i].workTypeCode, listTotalSubjects[i].workTypeAtr));
                }
                this.workTypeInfo = ko.observable(null);
                this.workingInfo = ko.observable(null);

            }
        }

        export class TotalCondition {
            upperLimitSettingAtr: KnockoutObservable<number>;
            lowerLimitSettingAtr: KnockoutObservable<number>;
            thresoldUpperLimit: KnockoutObservable<number>;
            thresoldLowerLimit: KnockoutObservable<number>;
            constructor(upperLimitSettingAtr: number, lowerLimitSettingAtr: number, thresoldUpperLimit: number, thresoldLowerLimit: number) {
                this.upperLimitSettingAtr = ko.observable(upperLimitSettingAtr);
                this.lowerLimitSettingAtr = ko.observable(lowerLimitSettingAtr);
                this.thresoldUpperLimit = ko.observable(thresoldUpperLimit);
                this.thresoldLowerLimit = ko.observable(thresoldLowerLimit);
            }
        }

        export class TotalSubjects {
            workTypeCode: KnockoutObservable<string>;
            workTypeAtr: KnockoutObservable<number>;

            constructor(workTypeCode: string, workTypeAtr: number) {
                this.workTypeCode = ko.observable(workTypeCode);
                this.workTypeAtr = ko.observable(workTypeAtr);
            }
        }
    }
}