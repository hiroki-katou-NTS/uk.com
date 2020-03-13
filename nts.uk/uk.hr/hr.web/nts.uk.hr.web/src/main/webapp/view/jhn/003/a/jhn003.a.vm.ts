module jhn003.a.vm {
    import setShared = nts.uk.ui.windows.setShared;
    import text = nts.uk.resource.getText;
    import jump = nts.uk.request.jump;
    import dialog = nts.uk.ui.dialog;
    import block = nts.uk.ui.block;

    export class ViewModel {

        searchInfo: KnockoutObservable<SearchInfo> = ko.observable(new SearchInfo());

        reportList: KnockoutObservableArray<SearchInfo> = ko.observableArray([]);
        
        approvalAllEnable: KnockoutObservableArray<boolean> = ko.observable(false);

        constructor() {
            let self = this;
            
            self.reportList.subscribe((data) => {

                if (data.length > 0 && self.searchInfo().approvalReport()) {
                    
                    self.approvalAllEnable(true);
                    
                } else {
                    
                    self.approvalAllEnable(false);
                    
                }
            });
            
            self.searchInfo().approvalReport.subscribe((data) => {
                self.approvalAllEnable(false);
                self.searchInfo().approvalStatus(data ? 1 : null);
            });
            
            setTimeout(function() {
                $(window).resize(function() {
                    $("#reportList").igGrid("option", "height", (window.innerHeight - 320) + "px");
                });
            }, 100);
        }

        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();

            block.grayout();

            service.startPage().done((data) => {

                let reportItems = [{ code: null, name: "" }];

                self.searchInfo().reportItems(reportItems.concat(_.map(data, x => { return { code: x.reportClsId, name: x.reportName } })));

                self.bindReportList();
            }).fail((error) => {

                dialog.info(error);

            }).always(() => {
                    dfd.resolve();
                    block.clear();
                });


            return dfd.promise();
        }

        approvalAll() {
            let self = this,
                command = ko.toJS(self.searchInfo());

            $("#A222_1_1").trigger("validate");

            if (nts.uk.ui.errors.hasError()) { return; }

            block.grayout();

            service.approvalAll(command).done(() => {

                dialog.info({ messageId: "MsgJ_47" });

            })
                .fail((error) => {

                    dialog.info(error);

                })
                .always(() => {

                    block.clear();

                });

        }

        search() {
            let self = this;

            $("#A222_1_1").trigger("validate");

            if (nts.uk.ui.errors.hasError()) { return; }

            block.grayout();

            let param  = ko.toJS(self.searchInfo());
            
            //set start date la dau ngay
            param.appDate.startDate.setHours(0, 0, 0, 0);
            //set end date la cuoi ngay
            param.appDate.endDate.setHours(23, 59, 59, 999);
            service.findPersonReport(param).done((data) => {

                self.reportList(_.map(data, x => new PersonReport(x)));
                self.bindReportList();

            }).fail((error) => {

                dialog.info(error);

            }).always(() => {
                block.clear();
            });
        }

        showModal(id, key, el) {
            let self = this,
                item = _.find(self.reportList(), ['reportID', id]);

            if (item) {
                if (item.approvalReport) {
                    jump("hr", "/view/jhn/001/c/index.xhtml?RPTID=" + item.reportID);
                } else {
                    jump("hr", "/view/jhn/001/a/index.xhtml?RPTID=" + item.reportID);
                }
            }

        }

        bindReportList(): void {

            let self = this,
                dataSources = self.reportList();

            if ($('#reportList').data("igGrid")) {
                $('#reportList').ntsGrid("destroy");
            };

            $('#reportList').ntsGrid({
                autoGenerateColumns: false,
                width: '908px',
                height: window.innerHeight - 320,
                primaryKey: 'reportID',
                virtualization: true,
                rowVirtualization: true,
                virtualizationMode: 'continuous',
                hidePrimaryKey: true,
                columns: [
                    { headerText: '', key: 'reportID', width: 0, hidden: true },
                    { headerText: text('JHN003_A222_9_2'), key: 'link', dataType: 'string', width: '70px', ntsControl: 'ShowDetails' },
                    { headerText: text('JHN003_A222_9_3'), key: 'appBussinessName', dataType: 'string', width: '90px' },
                    { headerText: text('JHN003_A222_9_4'), key: 'reportName', dataType: 'string', width: '130px' },
                    { headerText: text('JHN003_A222_9_5'), key: 'appDate', dataType: 'string', width: '110px' },
                    { headerText: text('JHN003_A222_9_6'), key: 'reportDetail', dataType: 'string', width: '200px' },
                    { headerText: text('JHN003_A222_9_7'), key: 'inputBussinessName', dataType: 'string', width: '90px' },
                    { headerText: text('JHN003_A222_9_8'), key: 'inputDate', dataType: 'string', width: '110px' },
                    { headerText: text('JHN003_A222_9_9'), key: 'aprStatus', dataType: 'string', width: '90px' }

                ],
                dataSource: dataSources,
                features: [
                    {
                        name: 'Filtering', type: 'local', mode: 'simple',
                        dataFiltered: function(evt, ui) {
                        }
                    },
                    { name: 'Resizing' },
                    { name: 'MultiColumnHeaders' }
                ],
                ntsFeatures: [
                    { name: 'CopyPaste' }
                ],
                ntsControls: [
                    { name: 'ShowDetails', click: function(id, key, el) { self.showModal(id, key, el); }, controlType: 'LinkLabel' }
                ]
            });
        }


    }

    interface IPersonReport {
        reportID: string;
        appBussinessName: string;
        reportName: string;
        appDate: Date;
        approvalReport: boolean;
        reportDetail: string;
        inputBussinessName: string;
        inputDate: Date;
        aprStatus: StatusType;
    }

    class PersonReport {
        reportID: string;
        link: string = text('JHN003_A222_9_22');
        appBussinessName: string;
        reportName: string;
        appDate: Date;
        approvalReport: boolean;
        reportDetail: string;
        inputBussinessName: string;
        inputDate: Date;
        aprStatus: string;
        constructor(data: IPersonReport) {
            this.reportID = data.reportID;
            this.approvalReport = data.approvalReport;
            this.appBussinessName = data.appBussinessName;
            this.reportName = data.reportName;
            this.appDate = data.appDate;
            this.reportDetail = data.reportDetail;
            this.inputBussinessName = data.inputBussinessName + (data.appBussinessName != data.inputBussinessName ? text('JHN003_A222_9_27_1') : "");
            this.inputDate = data.inputDate;
            this.aprStatus = text(StatusType[data.aprStatus]);

        }
    }

    class SearchInfo {
        appDate: KnockoutObservable<any> = ko.observable({ startDate: moment(new Date()).add(-1, 'M').toDate(), endDate: moment(new Date()).add(1, 'M').toDate() });
        inputName: KnockoutObservable<string> = ko.observable('');
        approvalReport: KnockoutObservable<boolean> = ko.observable(false);
        reportItems: KnockoutObservableArray<ItemModel> = ko.observableArray([
//            { code: null, name: "" },
//            { code: "0", name: "育児休業申請届" },
//            { code: "1", name: "育児短時間勤務申請届" },
//            { code: "2", name: "介護休暇届" },
//            { code: "3", name: "介護休業申請届" }
        ]);
        reportId: KnockoutObservable<string> = ko.observable('');
        approvalItems: KnockoutObservableArray<ItemModel> = ko.observableArray([
            { code: null, name: "" },
            { code: "1", name: text("JHN003_A222_4_1_1") },
            { code: "2", name: text("JHN003_A222_4_1_2") },
            { code: "4", name: text("JHN003_A222_4_1_4") },
            { code: "5", name: text("JHN003_A222_4_1_5") },
            { code: "6", name: text("JHN003_A222_4_1_6") }
        ]);
        approvalStatus: KnockoutObservable<string> = ko.observable('0');

        constructor() {
            
            let self = this;
            
            self.appDate.subscribe((data) => {
                if (data.endDate && data.startDate > data.endDate) {
                    data.endDate = data.startDate;
                }

            });
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

    enum StatusType {
        "未着手" = 0,
        "JHN003_A222_4_1_1" = 1,
        "JHN003_A222_4_1_2" = 2,
        "JHN003_A222_4_1_3" = 3,
        "JHN003_A222_4_1_4" = 4,
        "JHN003_A222_4_1_5" = 5,
        "JHN003_A222_4_1_6" = 6,
        "反映前承認待ち" = 7,
        "反映待ち" = 8,
        "反映済" = 9,
    }
}