module nts.uk.at.view.kaf002_ref.d.viewmodel {
    import AppType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.AppType;
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import PrintContentOfEachAppDto = nts.uk.at.view.kaf000_ref.shr.viewmodel.PrintContentOfEachAppDto;

    
    @component({
        name: 'kaf002-d',
        template: '/nts.uk.at.web/view/kaf_ref/002/d/index.html'
    })
    class Kaf002DViewModel extends ko.ViewModel {
        appType: KnockoutObservable<number> = ko.observable(AppType.STAMP_APPLICATION);
        appDispInfoStartupOutput: any;
        approvalReason: KnockoutObservable<string>;
        application: KnockoutObservable<Application>;
        
        
        dataSource: KnockoutObservableArray<ItemModel>;
        dataSourceReason: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        selectedCodeReason: KnockoutObservable<string>;
        time: KnockoutObservable<number>;
        isSendMail: KnockoutObservable<boolean> = ko.observable(false);
        data: any;
        comment1: KnockoutObservable<string> = ko.observable('comment1');
        comment2: KnockoutObservable<string> = ko.observable('comment2');
    
    
       fetchData() {
           const self = this;
           self.$blockui('show');
           let appplication = ko.toJS(self.application) as Application;
           let appId = appplication.appID;
           let companyId = self.$user.companyId;
           let appDispInfoStartupDto = ko.toJS(self.appDispInfoStartupOutput);
           let recoderFlag = true;
           let command = {
                   appId,
                   companyId,
                   appDispInfoStartupDto,
                   recoderFlag
           }
           self.$ajax(API.getDetail, command)
               .done(res => {
                   console.log(res);
                   self.data = res;
                   self.selectedCode(String(self.data.appRecordImage.appStampCombinationAtr));
                   self.time(Number(self.data.appRecordImage.attendanceTime));
                   if (self.data.appRecordImage.appStampGoOutAtr) {
                       self.selectedCodeReason(String(self.data.appRecordImage.appStampGoOutAtr));
                   }
                   
                   
               }).fail(res => {
                   console.log('fail');
               }).always(() => {
                   self.$blockui('hide');
               });
       }
       created( params: { 
           application: any,
           printContentOfEachAppDto: PrintContentOfEachAppDto,
           approvalReason: any,
           appDispInfoStartupOutput: any, 
           eventUpdate: (evt: () => void ) => void
       }) {
           
           const self = this;
           // bind common
           self.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
           self.application = params.application;
           self.approvalReason = params.approvalReason;
           
           
           let itemModelList = [];
           let itemModelReasonList = [];
           _.forEach(new EngraveAtrObject(), prop => {
               itemModelList.push(new ItemModel(String(prop.value), prop.name))
           });
           _.forEach(new GoOutReasonAtrObject(), prop => {
               itemModelReasonList.push(new ItemModel(String(prop.value), prop.name))
           });
           self.dataSource = ko.observableArray(itemModelList);
           self.dataSourceReason = ko.observableArray(itemModelReasonList);
           
           self.selectedCode = ko.observable('1');
           self.selectedCodeReason = ko.observable('0');
           
           // initial time 
           self.time = ko.observable(null);
           
           params.eventUpdate(self.update.bind(self));
           
           self.fetchData();
           
       }
       update() {
           console.log('update');
           const self = this;
           if (!self.appDispInfoStartupOutput().appDetailScreenInfo) {
               return;
           }
           let application = ko.toJS(self.application);
           let applicationDto = self.appDispInfoStartupOutput().appDetailScreenInfo.application as any;
           applicationDto.prePostAtr = application.prePostAtr;
           applicationDto.opAppReason = application.opAppReason;
           applicationDto.opAppStandardReasonCD = application.opAppStandardReasonCD;    
           applicationDto.opReversionReason = application.opReversionReason;
           let recoderFlag = true;
           let appStampOutputDto = self.data;
           let appRecordImageDto = {
                   appStampCombinationAtr: Number(ko.toJS(self.selectedCode)),
                   attendanceTime: Number(ko.toJS(self.time)),
                   appStampGoOutAtr: Number(ko.toJS(self.selectedCode)) != 3 ? null : Number(ko.toJS(self.selectedCodeReason))
           };
           let command = {
                   applicationDto,
                   recoderFlag,
                   appRecordImageDto,
                   appStampOutputDto
           }
           self.$blockui('show');
           self.$ajax(API.update, command)
               .done(res => {
                   console.log(res);
               }).fail(res => {
                   console.log(res);
               }).always(() => {
                   self.$blockui('hide');
               })
       }
        
    }
 class EngraveAtrObject {
        
        ATTENDANCE = {value : EngraveAtr.ATTENDANCE, name : '出勤'};
        OFFICE_WORK = {value : EngraveAtr.OFFICE_WORK, name : '退勤'};
        OVERTIME = {value : EngraveAtr.OVERTIME, name : '退勤（残業）'};
        GO_OUT = {value : EngraveAtr.GO_OUT, name : '外出'};
        RETURN = {value : EngraveAtr.RETURN, name : '戻り'};
        EARLY = {value : EngraveAtr.EARLY, name : '早出'};
        HOLIDAY = {value : EngraveAtr.HOLIDAY, name : '休出'};
        
    }
    class GoOutReasonAtrObject {
        
        PRIVATE = {value: GoOutReasonAtr.PRIVATE, name: '私用'};
        PUBLIC = {value: GoOutReasonAtr.PUBLIC, name: '公用'};
        COMPENSATION = {value: GoOutReasonAtr.COMPENSATION, name: '有償'};
        UNION = {value: GoOutReasonAtr.UNION, name: '組合'};
        
    }
    enum EngraveAtr {
        /**
         * 出勤
         */
        ATTENDANCE,
        
        /**
         * 退勤
         */
        OFFICE_WORK,
        
        /**
         * 退勤（残業）
         */
        OVERTIME,
        
        /**
         * 外出
         */
        GO_OUT,
        
        /**
         * 戻り
         */
        RETURN,
        
        /**
         * 早出
         */
        EARLY,
        
        /**
         * 休出
         */
        HOLIDAY
    }
    enum GoOutReasonAtr {
        /**
         * 私用
         */
        PRIVATE,
        
        /**
         * 公用
         */
        PUBLIC,
        
        /**
         * 有償
         */
        COMPENSATION,
        
        /**
         * 組合
         */
        UNION
    }
    class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    const API = {
            getDetail: "at/request/application/stamp/detailAppStamp",
            update: "at/request/application/stamp/updateNew",
            checkUpdate: "at/request/application/stamp/checkBeforeUpdate"
            
        }
}