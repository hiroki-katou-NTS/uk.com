module nts.uk.at.view.kdw006.c.viewmodel {
    export class ScreenModelC {
        daiPerformanceFunDto: KnockoutObservable<DaiPerformanceFunDto>;
        approvalProcessDto: KnockoutObservable<ApprovalProcessDto>;
        identityProcessDto: KnockoutObservable<IdentityProcessDto>
        monPerformanceFunDto: KnockoutObservable<MonPerformanceFunDto>;
        itemList: KnockoutObservableArray<any>;
        constructor() {
            let self = this;

            self.itemList = ko.observableArray([
                new BoxModel(0, 'エラーがあっても確認できる'),
                new BoxModel(1, 'エラーを訂正するまでチェックできない'),
                new BoxModel(2, 'エラーを訂正するまで登録できない')
            ]);
            
            self.daiPerformanceFunDto = ko.observable(new DaiPerformanceFunDto({
                    cid: '',
                    comment: '',
                    isCompleteConfirmOneMonth: 0,
                    isDisplayAgreementThirtySix: 0,
                    isFixClearedContent: 0,
                    isDisplayFlexWorker: 0,
                    isUpdateBreak: 0,
                    isSettingTimeBreak: 0,
                    isDayBreak: 0,
                    isSettingAutoTime: 0,
                    isUpdateEarly: 0,
                    isUpdateOvertime: 0,
                    isUpdateOvertimeWithinLegal: 0,
                    isFixContentAuto: 0,
                }));
            self.approvalProcessDto = ko.observable(new ApprovalProcessDto({
                cid: '',
                jobTitleId: '',
                useDayApproverConfirm: 0,
                useMonthApproverComfirm: 0,
                supervisorConfirmError: 0,
            }));
            self.identityProcessDto = ko.observable(new IdentityProcessDto({
                cid: '',
                useConfirmByYourself: 0,
                useIdentityOfMonth: 0,
                yourselfConfirmError: 0
            }));
            self.monPerformanceFunDto = ko.observable(new MonPerformanceFunDto({
                cid: '',
                comment: '',
                isConfirmDaily: 0
            }));
        }


        start(): JQueryPromise<any> {
            let self = this;
            return self.getFuncRestric();
        }

        //Get Function Restriction
        getFuncRestric(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getFuncRestric().done(function(data) {
                dfd.resolve();
            });
            return dfd.promise();
        }

        saveData() {
            let self = this;
            let funcRestric = {
            };
            service.update(funcRestric).done(function() {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
            });
        }
   }
    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
    interface IDaiPerformanceFunDto {
        cid: string;
        comment: string;
        isCompleteConfirmOneMonth: number;
        isDisplayAgreementThirtySix: number;
        isFixClearedContent: number;
        isDisplayFlexWorker: number;
        isUpdateBreak: number;
        isSettingTimeBreak: number;
        isDayBreak: number;
        isSettingAutoTime: number;
        isUpdateEarly: number;
        isUpdateOvertime: number;
        isUpdateOvertimeWithinLegal: number;
        isFixContentAuto: number;
    }
        
    class DaiPerformanceFunDto {
        cid: KnockoutObservable<string>;
        comment: KnockoutObservable<string>;
        isCompleteConfirmOneMonth: KnockoutObservable<boolean>;
        isDisplayAgreementThirtySix: KnockoutObservable<boolean>;
        isFixClearedContent: KnockoutObservable<boolean>;
        isDisplayFlexWorker: KnockoutObservable<boolean>;
        isUpdateBreak: KnockoutObservable<boolean>;
        isSettingTimeBreak: KnockoutObservable<boolean>;
        isDayBreak: KnockoutObservable<boolean>;
        isSettingAutoTime: KnockoutObservable<boolean>;
        isUpdateEarly: KnockoutObservable<boolean>;
        isUpdateOvertime: KnockoutObservable<boolean>;
        isUpdateOvertimeWithinLegal: KnockoutObservable<boolean>;
        isFixContentAuto: KnockoutObservable<boolean>;
        
        constructor (param: IDaiPerformanceFunDto) {
            let self = this;
            self.cid = ko.observable(param.cid);
            self.comment = ko.observable(param.comment);
            self.isCompleteConfirmOneMonth = ko.observable(param.isCompleteConfirmOneMonth == 1 ? true: false);
            self.isDisplayAgreementThirtySix = ko.observable(param.isDisplayAgreementThirtySix == 1 ? true: false);
            self.isFixClearedContent = ko.observable(param.isFixClearedContent == 1 ? true: false);
            self.isDisplayFlexWorker = ko.observable(param.isDisplayFlexWorker == 1 ? true: false);
            self.isUpdateBreak = ko.observable(param.isUpdateBreak == 1 ? true: false);
            self.isSettingTimeBreak = ko.observable(param.isSettingTimeBreak == 1 ? true: false);
            self.isDayBreak = ko.observable(param.isDayBreak == 1 ? true: false);
            self.isSettingAutoTime = ko.observable(param.isSettingAutoTime == 1 ? true: false);
            self.isUpdateEarly = ko.observable(param.isUpdateEarly == 1 ? true: false);
            self.isUpdateOvertime = ko.observable(param.isUpdateOvertime == 1 ? true: false);
            self.isUpdateOvertimeWithinLegal = ko.observable(param.isUpdateOvertimeWithinLegal == 1 ? true: false);
            self.isFixContentAuto = ko.observable(param.isFixContentAuto == 1 ? true: false);
        }
    }

    interface IApprovalProcessDto {
        cid: string;
        jobTitleId: string;
        useDayApproverConfirm: number;
        useMonthApproverComfirm: number;
        supervisorConfirmError: number;
    }
    class ApprovalProcessDto {
        cid: KnockoutObservable<string>;
        jobTitleId: KnockoutObservable<string>;
        useDayApproverConfirm: KnockoutObservable<boolean>;
        useMonthApproverComfirm: KnockoutObservable<boolean>;
        supervisorConfirmError: KnockoutObservable<number>;
        
        constructor (param: IApprovalProcessDto) {
            let self = this;
            self.cid = ko.observable(param.cid);
            self.jobTitleId = ko.observable(param.jobTitleId);
            self.useDayApproverConfirm = ko.observable(param.useDayApproverConfirm == 1 ? true: false );
            self.useMonthApproverComfirm = ko.observable(param.useMonthApproverComfirm == 1 ? true : false);
            self.supervisorConfirmError = ko.observable(param.supervisorConfirmError);
        }
    }
    
    interface IIdentityProcessDto {
        cid: string;
        useConfirmByYourself: number;
        useIdentityOfMonth: number;
        yourselfConfirmError: number;
    }
    class IdentityProcessDto {
        cid: KnockoutObservable<string>;
        useConfirmByYourself: KnockoutObservable<boolean>;
        useIdentityOfMonth: KnockoutObservable<boolean>;
        yourselfConfirmError: KnockoutObservable<number>;
        
        constructor (param: IIdentityProcessDto) {
            let self = this;
            self.cid(param.cid);
            self.useConfirmByYourself = ko.observable(param.useConfirmByYourself == 1 ? true: false);
            self.useIdentityOfMonth = ko.observable(param.useIdentityOfMonth == 1 ? true: false);
            self.yourselfConfirmError = ko.observable(param.yourselfConfirmError);
        }
    }
    interface IMonPerformanceFunDto {
        cid: string;
        comment: string;
        isConfirmDaily: number;    
    }
    class MonPerformanceFunDto {
        cid: KnockoutObservable<string>;
        comment: KnockoutObservable<string>;
        isConfirmDaily: KnockoutObservable<boolean>;     
        constructor (param: IMonPerformanceFunDto ) {
            let self = this;
            self.cid = ko.observable(param.cid);
            self.comment = ko.observable(param.comment);
            self.isConfirmDaily = ko.observable(param.isConfirmDaily == 1 ? true: false);
        }
    }
}
