module nts.uk.at.view.kaf018.d.viewmodel {
    import text = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    import formatDate = nts.uk.time.formatDate;
    import shareModel = kaf018.share.model.REFLECTEDSTATUS;
    import shared = kaf018.share.model;
    export class ScreenModel {
        selectedEmpId: KnockoutObservable<string> = ko.observable('');
        listStatusEmp: Array<ApprovalStatusEmployee> = [];
        
        columns: KnockoutObservableArray<NtsGridListColumn>;
        listData: Array<Content> = [];
        listDataDisp: KnockoutObservableArray<ContentDisp> = ko.observableArray([]);
        constructor() {
            var self = this;
//            var listAppStt: Array<number> = [0, 1, 2, 3];
//            let currentDate = new Date();
//            for (let i = 0; i < 30; i++) {
//                let startDate = currentDate.getDay();
//                let endDate = currentDate.getDay() + 1;
//                self.listData.push(new Content("" + i, "Anh" + i, true, startDate, endDate, "anhvsdvvfdvd" + i, i, listAppStt, "sdvsgvs", "sdvsdvs", "bfdbgf", "cvnvbn", "hgmvbnvb"));
//            }
//            console.log(self.listData);
        }

        /**
         * 起動する
         */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            let params = getShared("KAF018D_VALUE");
            if (params) {
                _.each(params.listStatusEmp, function(item) {
                     self.listStatusEmp.push(new ApprovalStatusEmployee(item.sId, new Date(item.startDate), new Date(item.endDate)));
                });
            }
            let paramsTranfer = {
                listStatusEmp: self.listStatusEmp,
                selectedEmpId: params.selectedEmpId
            };
            service.initApprovalSttRequestContentDis(paramsTranfer).done(function(data: any) {
                self.listData = data;
                self.initExTable(self.listData);
                dfd.resolve();
            }).fail(function(data: any) {
                dfd.reject();
                });
            return dfd.promise();
        }

        /**
         * Create exTable
         */
        initExTable(listData: Array<Content>): void {
            var self = this;
            _.each(listData, function(data: Content) {
                let dateRange = self.appDateRangeColor(self.convertDateMDW(data.appStartDate), self.convertDateMDW(data.appEndDate));
                self.listDataDisp.push(new ContentDisp(data.appType, data.appName, data.prePostAtr ? "事前" : "事後", dateRange, data.appContent, self.disReflectionStatus(data.reflectState), self.disApprovalStatus(data.approvalStatus), data.phase1, data.phase2, data.phase3, data.phase4, data.phase5));
            });
            let colorBackGr = self.fillColorbackGr();
            self.reloadGridApplicaion(colorBackGr);
        }

        reloadGridApplicaion(colorBackGr: any) {
            var self = this;
            $("#grid1").ntsGrid({
                width: '1120px',
                height: '500px',
                dataSource: self.listDataDisp(),
                primaryKey: 'appId',
                virtualization: true,
                rows: 20,
                hidePrimaryKey: true,
                rowVirtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: "", key: 'appId', dataType: 'string', width: '0px', hidden: true },
                    { headerText: text("KAF018_36"), key: 'appName', dataType: 'string', width: 160 },
                    { headerText: text("KAF018_37"), key: 'prePostAtr', dataType: 'string', width: 120 },
                    { headerText: text("KAF018_38"), key: 'appDate', dataType: 'string', width: 150 },
                    { headerText: text("KAF018_39"), key: 'appContent', dataType: 'string', width: 450 },
                    { headerText: text("KAF018_40"), key: 'reflectState', dataType: 'string', width: 100 },
                    { headerText: text("KAF018_41"), key: 'approvalStatus', dataType: 'string', width: 150 },
                    { headerText: text("KAF018_42"), key: 'phase1', dataType: 'string', width: 150 },
                    { headerText: text("KAF018_43"), key: 'phase2', dataType: 'string', width: 150 },
                    { headerText: text("KAF018_44"), key: 'phase3', dataType: 'string', width: 150 },
                    { headerText: text("KAF018_45"), key: 'phase4', dataType: 'string', width: 150 },
                    { headerText: text("KAF018_46"), key: 'phase5', dataType: 'string', width: 150 }
                ],
                features: [
                    {
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: true
                    }
                ],
                ntsFeatures: [
                    {
                        name: 'CellState',
                        rowId: 'rowId',
                        columnKey: 'columnKey',
                        state: 'state',
                        states: colorBackGr
                    }
                ]
            });
        }

        fillColorbackGr(): Array<shared.CellState> {
            let self = this;
            let result = [];
            _.each(self.listDataDisp(), function(item) {
                let rowId = item.appId;
                //fill color in 承認状況
                if (item.reflectState == 0) {//0 下書き保存/未反映　=　未
                    result.push(new shared.CellState(rowId, 'reflectState', ['unapprovalCell']));
                }
                if (item.reflectState == 1) {//1 反映待ち　＝　承認済み
                    result.push(new shared.CellState(rowId, 'reflectState', ['approvalCell']));
                }
                if (item.reflectState == 2) {//2 反映済　＝　反映済み
                    result.push(new shared.CellState(rowId, 'reflectState', ['reflectCell']));
                }
                if (item.reflectState == 3 || item.reflectState == 4) {//3,4 取消待ち/取消済　＝　取消
                    result.push(new shared.CellState(rowId, 'reflectState', ['cancelCell']));
                }
                if (item.reflectState == 5) {//5 差し戻し　＝　差戻
                    result.push(new shared.CellState(rowId, 'reflectState', ['remandCell']));
                }
                if (item.reflectState == 6) {//6 否認　=　否
                    result.push(new shared.CellState(rowId, 'reflectState', ['denialCell']));
                }
            });
            return result;
        }
        //反映状況
        disReflectionStatus(status: number): string {
            switch (status) {
                case shareModel.NOTREFLECTED:
                    return text('KAF018_93');
                case shareModel.WAITREFLECTION:
                    return text('KAF018_94');
                case shareModel.REFLECTED:
                    return text('KAF018_95');
                case shareModel.DENIAL:
                    return text('KAF018_96');
                case shareModel.REMAND:
                    return text('KAF018_97');
                case shareModel.WAITCANCEL:
                    return text('KAF018_98');
                case shareModel.CANCELED:
                    return text('KAF018_98');
            }
        }

        //承認状況
        disApprovalStatus(listAppStatus: Array<number>): string {
            var disAppStt: string = "";
            _.each(listAppStatus, function(appStt) {
                if (appStt == 0) {
                    disAppStt += '<span class = "unapprovaled-remand">' + "－" + '</span>';
                } else if (appStt == 1) {
                    disAppStt += '<span class = "approvaled">' + "〇" + '</span>';
                } else if (appStt == 2) {
                    disAppStt += '<span class = "denied">' + "×" + '</span>';
                }
            });
            return disAppStt;
        }

        closeUp() {
            //画面を閉じる
            nts.uk.ui.windows.close();
        }
        //MM/dd ver24
        convertDateMDW(date: string) {
            let a: number = moment(date, 'YYYY/MM/DD').isoWeekday();
            let toDate = moment(date, 'YYYY/MM/DD').toDate();
            let dateMDW = (toDate.getMonth() + 1) + '/' + toDate.getDate();
            switch (a) {
                case 1://Mon
                    return dateMDW + '(月)';
                case 2://Tue
                    return dateMDW + '(火)';
                case 3://Wed
                    return dateMDW + '(水)';
                case 4://Thu
                    return dateMDW + '(木)';
                case 5://Fri
                    return dateMDW + '(金)';
                case 6://Sat
                    return dateMDW + '(土)';
                default://Sun
                    return dateMDW + '(日)';
            }
        }

        appDateRangeColor(startDate: string, endDate: string): string {
            let sDate = '<div class = "dateRange" >' + startDate + '</div>';;
            let eDate = '<div class = "dateRange" >' + endDate + '</div>';
            //color text appDate
            let a = startDate.split("(")[1];
            let b = endDate.split("(")[1];
            let color1 = a.substring(0, 1);
            if (color1 == '土') {//土
                sDate = '<div class = "saturdayCell  dateRange" >' + startDate + '</div>';
            }
            if (color1 == '日') {//日 
                sDate = '<div class = "sundayCell  dateRange" >' + startDate + '</div>';
            }
            let color2 = b.substring(0, 1);
            if (color2 == '土') {//土
                eDate = '<div class = "saturdayCell  dateRange" >' + endDate + '</div>';
            }
            if (color2 == '日') {//日 
                eDate = '<div class = "sundayCell  dateRange" >' + endDate + '</div>';
            }
            return sDate + '<div class = "dateRange" >' + '－' + '</div>' + eDate;
        }
    }

    class ApprovalStatusEmployee {
         sId: string;
         startDate: Date;
         endDate: Date;
        constructor(sId: string, startDate: Date, endDate: Date) {
            this.sId = sId;
            this.startDate = startDate;
            this.endDate = endDate;
        }    
    }

    class NtsGridListColumn {
        headerText: string;
        key: string;
        width: number;
    }
    class Content {
        appType: string;
        appName: string;
        prePostAtr: boolean;
        appStartDate: Date;
        appEndDate: Date;
        appContent: string;
        reflectState: number;
        approvalStatus: Array<number>;
        phase1: string;
        phase2: string;
        phase3: string;
        phase4: string;
        phase5: string;
        constructor(appType: string, appName: string, prePostAtr: boolean, appStartDate: Date, appEndDate: Date, appContent: string, reflectState: number,
            approvalStatus: Array<number>, phase1: string, phase2: string, phase3: string, phase4: string, phase5: string) {
            this.appType = appType;
            this.appName = appName;
            this.prePostAtr = prePostAtr;
            this.appStartDate = appStartDate;
            this.appEndDate = appEndDate;
            this.appContent = appContent;
            this.reflectState = reflectState;
            this.approvalStatus = approvalStatus;
            this.phase1 = phase1;
            this.phase2 = phase2;
            this.phase3 = phase3;
            this.phase4 = phase4;
            this.phase5 = phase5;
        }
    }

    class ContentDisp {
        appId: string;
        appName: string;
        prePostAtr: string;
        appDate: string;
        appContent: string;
        reflectState: number;
        approvalStatus: string;
        phase1: string;
        phase2: string;
        phase3: string;
        phase4: string;
        phase5: string;
        constructor(appId: string, appName: string, prePostAtr: string, appDate: string, appContent: string, reflectState: number,
            approvalStatus: string, phase1: string, phase2: string, phase3: string, phase4: string, phase5: string) {
            this.appId = appId;
            this.appName = appName;
            this.prePostAtr = prePostAtr;
            this.appDate = appDate;
            this.appContent = appContent;
            this.reflectState = reflectState;
            this.approvalStatus = approvalStatus;
            this.phase1 = phase1;
            this.phase2 = phase2;
            this.phase3 = phase3;
            this.phase4 = phase4;
            this.phase5 = phase5;
        }
    }
}