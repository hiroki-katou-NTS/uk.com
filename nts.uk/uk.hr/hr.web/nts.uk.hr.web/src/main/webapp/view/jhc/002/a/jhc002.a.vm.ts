module nts.uk.hr.view.jhc002.a.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import NtsGridListColumn = nts.uk.ui.NtsGridListColumn;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {

        masterId: KnockoutObservable<string>;
        histList: KnockoutObservableArray<any>;
        selectedHistId: KnockoutObservable<any>;
        listCareerType: KnockoutObservableArray<any>;

        //careerpart
        listCareer: KnockoutObservableArray<any>;
        careerClass: KnockoutObservableArray<any>;
        careerType: KnockoutObservableArray<any>;
        maxClassLevel: KnockoutObservable<number>;

        constructor() {
            var self = this;

            // history component            
            self.height = ko.observable("200px");
            self.labelDistance = ko.observable("30px");
            self.screenMode = ko.observable(1);
            self.masterId = ko.observable("");
            self.histList = ko.observableArray([]);
            self.selectedHistId = ko.observable('');
            self.pathGet = ko.observable(`careermgmt/careerpath/getDateHistoryItem`);
            self.pathAdd = ko.observable(`careermgmt/careerpath/saveDateHistoryItem`);
            self.pathUpdate = ko.observable(`careermgmt/careerpath/updateDateHistoryItem`);
            self.pathDelete = ko.observable(`careermgmt/careerpath/removeDateHistoryItem`);
            self.getQueryResult = (res) => {
                return _.map(res, h => {
                    return { histId: h.historyId, startDate: h.startDate, endDate: h.endDate, displayText: `${h.startDate} ～ ${h.endDate}` };
                });
            };
            self.getSelectedStartDate = () => {
                let selectedHist = _.find(self.histList(), h => h.histId === self.selectedHistId());
                if (selectedHist) return selectedHist.startDate;
            };
            self.commandAdd = (masterId, histId, startDate, endDate) => {
                return { startDate: moment(startDate).format("YYYY/MM/DD") }
            };
            self.commandUpdate = (masterId, histId, startDate, endDate) => {
                return {
                    historyId: histId,
                    startDate: moment(startDate).format("YYYY/MM/DD")
                }
            };
            self.commandDelete = (masterId, histId) => {
                return {
                    historyId: histId
                };
            };
            self.delVisible = ko.observable(true);
            self.delChecked = ko.observable();
            self.afterRender = () => {
                //alert("Load!");
            };
            self.afterAdd = () => {
                //alert("Added");
            };
            self.afterUpdate = () => {
                //alert("Updated");
            };
            self.afterDelete = () => {
                //alert("Deleted");
            };

            //table 
            self.listCareerType = ko.observableArray([]);
            $("#fixed-table").ntsFixedTable({ height: 246, width: 990 });

            self.selectedHistId.subscribe(function(newValue) {
                self.getCarrerPart(newValue);
            });

            self.listCareer = ko.observableArray([]);
            self.careerClass = ko.observableArray([]);
            self.careerType = ko.observableArray([]);
            self.maxClassLevel = ko.observable(1);

            //set width for table
            self.maxClassLevel.subscribe(function(newValue) {
                let width = newValue * 165;
                document.getElementsByClassName("fixed-table")[0].style.width = width + "px";
                document.getElementsByClassName("nts-fixed-header-wrapper")[0].style.width = width + "px";
                document.getElementsByClassName("nts-fixed-body-wrapper")[0].style.width = width + "px";
                document.getElementsByClassName("fixed-table")[0].style.width = width + "px";
                document.getElementsByClassName("fixed-table")[1].style.width = width + "px";
            });

        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            block.grayout();
            new service.getMaxClassLevel().done(function(data: any) {
                self.maxClassLevel(5);
                dfd.resolve();
            }).fail(function(error) {
                nts.uk.ui.dialog.error({ messageId: error.messageId });
            }).always(function() {
                nts.uk.ui.block.clear();
            });
            return dfd.promise();
        }
        private getCarrerPart(hisId: string): void {
            var self = this;
            let startDate = _.filter(self.histList(), ['histId', hisId])[0].startDate;
            let command = {
                historyId: hisId,
                startDate: moment(startDate).format("YYYY/MM/DD")
            }
            if (hisId != '') {
                block.grayout();
                new service.getCareerPart(command).done(function(data: any) {
                    data.careerClass.add({ id: "", code: "", name: "" });
                    console.log(data);
                    self.listCareer(data.career);
                    self.maxClassLevel(data.maxClassLevel);
                    self.careerType(_.orderBy(data.careerType, ['code'], ['asc']));
                    self.careerClass(_.orderBy(data.careerClass, ['code'], ['asc']));
                    let list = [];
                    _.forEach(data.careerType, function(value) {
                        console.log(value);
                        list.push(new ScreenItem(value.code, _.filter(data.career, ['careerTypeItem', value.id])));
                        //self.listCareerType.push(a);
                    });
                    self.listCareerType(list);
                    block.clear();
                }).fail(function(error) {
                    nts.uk.ui.dialog.error({ messageId: error.messageId });
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }
        }
        public openDialogB(careerType: any): void {
            var self = __viewContext.vm;
            let startDate = _.filter(self.histList(), ['histId', self.selectedHistId()])[0].startDate;
            let command = {
                historyId: careerType.id,
                startDate: moment(startDate).format("YYYY/MM/DD"),
                career: __viewContext.vm.listCareer()
            }
            if (careerType != '') {
                block.grayout();
                new service.checkDataCareer(command).done(function() {
                    nts.uk.request.jump("hr", "/view/jhc/002/b/index.xhtml");
                }).fail(function(error) {
                    nts.uk.ui.dialog.error({ messageId: error.messageId });
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }
        }
        public save(): void {
            var self = this;
            let kt = false;
            _.forEach(ko.toJS(self.listCareerType()), function(value) {
                kt = value.checkExistSelected(self.maxClassLevel());
                if (kt) return false;
            });
            if (kt) {
                nts.uk.ui.windows.sub.modal("../c/index.xhtml").onClosed(() => {
                    let data = nts.uk.ui.windows.getShared("isNotice");
                    console.log(data);
                });
            } else {
                nts.uk.ui.dialog.error({ messageId: 'MsgJ_50' });
            }
            let startDate = _.filter(self.histList(), ['histId', self.selectedHistId()])[0].startDate;

        }
    }

    class ScreenItem {
        code: KnockoutObservable<string> = ko.observable("");
        l1: KnockoutObservable<string> = ko.observable("");
        l2: KnockoutObservable<string> = ko.observable("");
        l3: KnockoutObservable<string> = ko.observable("");
        l4: KnockoutObservable<string> = ko.observable("");
        l5: KnockoutObservable<string> = ko.observable("");
        l6: KnockoutObservable<string> = ko.observable("");
        l7: KnockoutObservable<string> = ko.observable("");
        l8: KnockoutObservable<string> = ko.observable("");
        l9: KnockoutObservable<string> = ko.observable("");
        l10: KnockoutObservable<string> = ko.observable("");
        constructor(code: string, career: Array<any>) {
            var self2 = this;
            self2.code = ko.observable(code);
            
            for (let i = 0; i < career.length; i++) {
                switch (career[i].careerLevel) {
                    case 1:
                        self2.l1(career[i].careerClassItem);
                        break;
                    case 2:
                        self2.l2(career[i].careerClassItem);
                        break;
                    case 3:
                        self2.l3(career[i].careerClassItem);
                        break;
                    case 4:
                        self2.l4(career[i].careerClassItem);
                        break;
                    case 5:
                        self2.l5(career[i].careerClassItem);
                        break;
                    case 6:
                        self2.l6(career[i].careerClassItem);
                        break;
                    case 7:
                        self2.l7(career[i].careerClassItem);
                        break;
                    case 8:
                        self2.l8(career[i].careerClassItem);
                        break;
                    case 9:
                        self2.l9(career[i].careerClassItem);
                        break;
                    case 10:
                        self2.l10(career[i].careerClassItem);
                        break;
                    default:
                        break;
                }
            }
        }
        
        public checkExistSelected(maxLever: number): boolean {
            if (maxLever == 0) return false;
            if (maxLever >= 1) {
                if (this.l1() != "") return true;
            }
            if (maxLever >= 2) {
                if (this.l2() != "") return true;
            }
            if (maxLever >= 3) {
                if (this.l3() != "") return true;
            }
            if (maxLever >= 4) {
                if (this.l4() != "") return true;
            }
            if (maxLever >= 5) {
                if (this.l5() != "") return true;
            }
            if (maxLever >= 6) {
                if (this.l6() != "") return true;
            }
            if (maxLever >= 7) {
                if (this.l7() != "") return true;
            }
            if (maxLever >= 8) {
                if (this.l8() != "") return true;
            }
            if (maxLever >= 9) {
                if (this.l9() != "") return true;
            }
            if (maxLever == 10) {
                if (this.l10() != "") return true;
            }
            return false;
        }
    }
    class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}
