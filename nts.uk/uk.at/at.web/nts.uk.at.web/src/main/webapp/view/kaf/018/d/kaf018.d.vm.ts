module nts.uk.at.view.kaf018.d.viewmodel {
    import text = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    import formatDate = nts.uk.time.formatDate;
    import shareModel = kaf018.share.model;

    export class ScreenModel {
        closureId: string;
        closureName: string;
        processingYm: string;
        startDateFormat: string;
        endDateFormat: string;
        startDate: Date;
        endDate: Date;
        listWorkplace: any;
        selectedWplIndex: number;
        selectedWplId: KnockoutObservable<string>;
        selectedWplName: KnockoutObservable<string>;
        listEmpCd: Array<string>;
        listWkpId: Array<string> = [];

        enableNext: KnockoutObservable<boolean>;
        enablePre: KnockoutObservable<boolean>;

        listData: any;
        constructor() {
            var self = this;
            self.listData = [];
            self.listWorkplace = [];
            self.selectedWplId = ko.observable('');
            self.selectedWplName = ko.observable('');
            self.enableNext = ko.observable(false);
            self.enablePre = ko.observable(false);
        }

        /**
         * 起動する
         */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            let params = getShared("KAF018D_PARAMS");
            if (params) {
                self.closureId = params.closureId;
                self.closureName = params.closureName;
                self.processingYm = params.processingYm;
                self.startDateFormat = formatDate(new Date(params.startDate), 'yyyy/MM/dd');
                self.endDateFormat = formatDate(new Date(params.endDate), 'yyyy/MM/dd');
                self.startDate = params.startDate;
                self.endDate = params.endDate;
                self.listWorkplace = params.listWorkplace;
                self.selectedWplIndex = params.selectedWplIndex;
                self.listEmpCd = params.listEmployeeCode;
            }
            self.initExTable();
            _.forEach(self.listWorkplace, function(item) {
                self.listWkpId.push(item.code);
            });
            dfd.resolve();
            return dfd.promise();
        }

        /**
         * Create exTable
         */
        initExTable(): void {
            var self = this;
            let sv2 = self.setSymbolForCellContentDetail();
            $.when(sv2).done(function(detailHeaderDeco) {
                var listData = [
                    new Content('1', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                    new Content('2', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                    new Content('3', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                    new Content('4', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                    new Content('5', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                    new Content('6', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                    new Content('7', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                    new Content('8', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                    new Content('9', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                    new Content('10', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                    new Content('11', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                    new Content('12', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                    new Content('13', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                    new Content('14', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                    new Content('15', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                    new Content('16', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                    new Content('17', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                    new Content('18', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                    new Content('19', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                    new Content('20', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                    new Content('21', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                    new Content('22', 'anh', 'duc', 'tuazn', 'bnhy', 'chi', 'nam', 'hai', 'chuc', 'dat', 'hung'),
                ]
                let initExTable = self.setFormatData(detailHeaderDeco, listData);

                new nts.uk.ui.exTable.ExTable($("#extable"), {
                    headerHeight: "30px", bodyRowHeight: "20px", bodyHeight: "390px",
                    horizontalSumBodyRowHeight: "0px",
                    areaResize: true,
                    bodyHeightMode: "fixed",
                    windowXOccupation: 50,
                    windowYOccupation: 20,
                    primaryTable: $("#extable")
                })
                    .DetailHeader(initExTable.detailHeader).DetailContent(initExTable.detailContent)
                    .create();
            });
        }

        setFormatData(detailHeaderDeco, listData) {
            var self = this;

            let detailHeaderColumns = [];
            let detailHeader = {};
            let detailContent = {};

            // create detail Columns and detail Content Columns
            detailHeaderColumns.push({ key: "applicationName", width: "160px", headerText: text("KAF018_36") });
            detailHeaderColumns.push({ key: "beforeAfter", width: "120px", headerText: text("KAF018_37") });
            detailHeaderColumns.push({ key: "applicationDate", width: "150px", headerText: text("KAF018_38") });
            detailHeaderColumns.push({ key: "applicationContent", width: "530px", headerText: text("KAF018_39") });
            detailHeaderColumns.push({ key: "reflectionStatus", width: "100px", headerText: text("KAF018_40") });
            detailHeaderColumns.push({ key: "approvalStatus", width: "150px", headerText: text("KAF018_41") });
            detailHeaderColumns.push({ key: "approvalPhase1", width: "100px", headerText: text("KAF018_42") });
            detailHeaderColumns.push({ key: "approvalPhase2", width: "100px", headerText: text("KAF018_43") });
            detailHeaderColumns.push({ key: "approvalPhase3", width: "100px", headerText: text("KAF018_44") });
            detailHeaderColumns.push({ key: "approvalPhase4", width: "100px", headerText: text("KAF018_45") });
            detailHeaderColumns.push({ key: "approvalPhase5", width: "100px", headerText: text("KAF018_46") });
            //create Detail Header
            detailHeader = {
                columns: detailHeaderColumns,
                width: "1200px",
                features: [
                    {
                        name: "HeaderRowHeight",
                        rows: { 0: "30px" }
                    },
                    {
                        name: "HeaderCellStyle",
                        decorator: detailHeaderDeco
                    }]
            };

            //create Detail Content
            detailContent = {
                columns: detailHeaderColumns,
                dataSource: listData,
                primaryKey: "applicationName"
            };
            return {
                detailHeader: detailHeader,
                detailContent: detailContent
            };
        }

        /**
         * Set symbol for cell detail
         * 
         */
        setSymbolForCellContentDetail(): JQueryPromise<any> {
            var self = this, dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        closeUp() {
            //画面を閉じる
            nts.uk.ui.windows.close();
        }
    }

    class Content {
        applicationName: string;
        beforeAfter: string;
        applicationDate: string;
        applicationContent: string;
        reflectionStatus: string;
        approvalStatus: string;
        approvalPhase1: string;
        approvalPhase2: string;
        approvalPhase3: string;
        approvalPhase4: string;
        approvalPhase5: string;
        constructor(applicationName: string, beforeAfter: string, applicationDate: string, applicationContent: string, reflectionStatus: string,
            approvalStatus: string, approvalPhase1: string, approvalPhase2: string, approvalPhase3: string, approvalPhase4: string, approvalPhase5: string) {
            this.applicationName = applicationName;
            this.beforeAfter = beforeAfter;
            this.applicationDate = applicationDate;
            this.applicationContent = applicationContent;
            this.reflectionStatus = reflectionStatus;
            this.approvalStatus = approvalStatus;
            this.approvalPhase1 = approvalPhase1;
            this.approvalPhase2 = approvalPhase2;
            this.approvalPhase3 = approvalPhase3;
            this.approvalPhase4 = approvalPhase4;
            this.approvalPhase5 = approvalPhase5;
        }
    }
}