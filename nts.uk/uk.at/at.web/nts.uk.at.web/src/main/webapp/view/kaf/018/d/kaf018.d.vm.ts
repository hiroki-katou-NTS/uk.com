module nts.uk.at.view.kaf018.d.viewmodel {
    import text = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    import formatDate = nts.uk.time.formatDate;
    import shareModel = kaf018.share.model;

    export class ScreenModel {

        selectedWplId: KnockoutObservable<string>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        dailySttOut: DailyStatusOut = new DailyStatusOut(null, null);
        listEmp: Array<any>;
        currentCode: KnockoutObservable<any>;
        listData: KnockoutObservableArray<Content> = ko.observableArray([]);
        listDataDisp : KnockoutObservableArray<ContentDisp> = ko.observableArray([]);
        constructor() {
            var self = this;
            self.selectedWplId = ko.observable('');
            self.columns = ko.observableArray([
                { headerText: text("KAF018_36"), key: 'applicationName', dataType: 'string', width: 160 },
                { headerText: text("KAF018_37"), key: 'beforeAfter', dataType: 'string', width: 120 },
                { headerText: text("KAF018_38"), key: 'applicationDate', dataType: 'string', width: 150 },
                { headerText: text("KAF018_39"), key: 'applicationContent', dataType: 'string', width: 450 },
                { headerText: text("KAF018_40"), key: 'reflectionStatus', dataType: 'string', width: 100 },
                { headerText: text("KAF018_41"), key: 'approvalStatus', dataType: 'string', width: 150 },
                { headerText: text("KAF018_42"), key: 'approvalPhase1', dataType: 'string', width: 150 },
                { headerText: text("KAF018_43"), key: 'approvalPhase2', dataType: 'string', width: 150 },
                { headerText: text("KAF018_44"), key: 'approvalPhase3', dataType: 'string', width: 150 },
                { headerText: text("KAF018_45"), key: 'approvalPhase4', dataType: 'string', width: 150 },
                { headerText: text("KAF018_46"), key: 'approvalPhase5', dataType: 'string', width: 150 }
            ]);
            this.currentCode = ko.observable();
        }

        /**
         * 起動する
         */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            let params = getShared("KAF018D_VALUE");
            if (params) {
                self.dailySttOut = params.dailyData;
                self.listEmp = params.listEmp;
                self.selectedWplId = params.selectWkkpId;
            }
//            service.initApprovalSttRequestContentDis(params).done(function(data: any) {
//                self.listData = data;
//            });
            
            self.initExTable();   
            dfd.resolve();      
            return dfd.promise();
        }

        /**
         * Create exTable
         */
        initExTable(): void {
            var self = this;
        }

        //申請日付
        getBeforeAfter(startDate: Date, endDate: Date) : string {
            startDate = endDate = new Date();
            
            if(startDate) {
                return moment(startDate).format('M/D (dd)') +"ー"+ moment(endDate).format('M/D (dd)');
            }
            return moment(endDate).format('M/D (dd)'); 
        }
        
        //反映状況
        disReflectionStatus() : void {
            
        }
        
        closeUp() {
            //画面を閉じる
            nts.uk.ui.windows.close();
        }
    }

    class DailyStatusOut {
        empId: string;
        listDaily: Array<Date>;
        constructor(empId: string, listDaily: Array<Date>) {
            this.empId = empId;
            this.listDaily = listDaily;
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
        beforeAfter: string;
        prePostAtr: number;
        appStartDate: Date;
        appEndDate:Date;
        appContent: string;
        reflectState: number;
        approvalStatus: Array<number>;
        phase1: string;
        phase2: string;
        phase3: string;
        phase4: string;
        phase5: string;
        constructor(appType: string, appName: string, beforeAfter: string, prePostAtr: number, appStartDate: Date, appEndDate: Date, appContent: string, reflectState: number,
            approvalStatus: Array<number>, phase1: string, phase2: string, phase3: string, phase4: string, phase5: string) {
            this.appType = appType;
            this.appName = appName;
            this.beforeAfter = beforeAfter;
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
        appName: string;
        beforeAfter: string;
        prePostAtr: string;
        appDate: string;
        appContent: string;
        reflectState: string;
        approvalStatus: string;
        phase1: string;
        phase2: string;
        phase3: string;
        phase4: string;
        phase5: string;
        constructor(appName: string, beforeAfter: string, prePostAtr: string, appDate: string, appContent: string, reflectState: string,
            approvalStatus: string, phase1: string, phase2: string, phase3: string, phase4: string, phase5: string) {
            this.appName = appName;
            this.beforeAfter = beforeAfter;
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