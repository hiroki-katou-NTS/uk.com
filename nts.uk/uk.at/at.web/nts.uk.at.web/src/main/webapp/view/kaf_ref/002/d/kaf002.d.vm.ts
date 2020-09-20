module nts.uk.at.view.kaf002_ref.d.viewmodel {
    import AppType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.AppType;
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import PrintContentOfEachAppDto = nts.uk.at.view.kaf000_ref.shr.viewmodel.PrintContentOfEachAppDto;
    
    const template = `
            <div>
    <div
        data-bind="component: { name: 'kaf000-b-component1', 
                                params: {
                                    appType: appType,
                                    appDispInfoStartupOutput: appDispInfoStartupOutput  
                                } }"></div>
    <div
        data-bind="component: { name: 'kaf000-b-component2', 
                                params: {
                                    appType: appType,
                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                } }"></div>
    <div
        data-bind="component: { name: 'kaf000-b-component3', 
                                params: {
                                    appType: appType,
                                    approvalReason: approvalReason,
                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                } }"></div>
    <div class="table">
        <div class="cell" style="width: 825px;" data-bind="component: { name: 'kaf000-b-component4',
                            params: {
                                appType: appType,
                                application: application,
                                appDispInfoStartupOutput: appDispInfoStartupOutput
                            } }"></div>
        <div class="cell" style="position: absolute;" data-bind="component: { name: 'kaf000-b-component9',
                            params: {
                                appType: appType,
                                application: application,
                                appDispInfoStartupOutput: $vm.appDispInfoStartupOutput
                            } }"></div>
    </div>
    <div
        data-bind="component: { name: 'kaf000-b-component5', 
                                params: {
                                    appType: appType,
                                    application: application,
                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                } }"></div>
    <div
        data-bind="component: { name: 'kaf000-b-component6', 
                                params: {
                                    appType: appType,
                                    application: application,
                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                } }"></div>
    <div class="label" data-bind="text: comment1().content, style: {color: comment1().color , margin:'10px', fontWeight: comment1().isBold ? 'bold' : 'normal'}" style="margin: 10px">
    </div>

    <div class="inlineBlockFirst">
        <!-- B6_1 -->
        <div class="labelFirst"
            data-bind="ntsFormLabel: {required: true}, html: $i18n('KAF002_79')"></div>
        <div style="margin-left: 40px"
            data-bind="ntsComboBox: {
                        options: dataSource,
                        optionsValue: 'code',
                        value: selectedCode,
                        optionsText: 'name',
                        required: true
                    }"></div>
    </div>

    <div class="blockSecond">
        <input class="inputBlockSecond" id="inputTimeKAF002"
            data-bind=" css: selectedCode() == 3 ? 'adjustWidth' : '', ntsTimeEditor: { value: time, required: true, inputFormat: 'time', constraint: 'SampleTimeDuration', mode: 'time'
                                                    }" />

        <div class="dropListBlockSecond"
            data-bind="visible: selectedCode() == 3, ntsComboBox: {
                        options: dataSourceReason,
                        optionsValue: 'code',
                        value: selectedCodeReason,
                        optionsText: 'name',
                        required: true
                    }"></div>
    </div>

    <div data-bind="text: comment2().content, style: {color: comment2().color , margin:'10px', fontWeight: comment2().isBold ? 'bold' : 'normal'}" class="label" style="margin: 10px"></div>
    <div
        data-bind="component: { name: 'kaf000-b-component7', 
                                params: {
                                    appType: appType,
                                    application: application,
                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                } }"></div>
    <div
        data-bind="component: { name: 'kaf000-b-component8', 
                                params: {
                                    appType: appType,
                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                } }"></div>

</div>
        
    `
    
    @component({
        name: 'kaf002-d',
        template: template
    })
    class Kaf002DViewModel extends ko.ViewModel {
        printContentOfEachAppDto: KnockoutObservable<PrintContentOfEachAppDto>;
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
        comment1: KnockoutObservable<Comment> = ko.observable(new Comment('', true, ''));
        comment2: KnockoutObservable<Comment> = ko.observable(new Comment('', true, ''));
    
        public bindDataStart(data: any) {
            const self = this;
    //        let listType = self.data.appStampSetting.goOutTypeDispControl;
    //        let listTypeItem = [];
    //        _.forEach(listType, i => {
    //            listTypeItem.push(new ItemModel(String(i.goOutType), i.display))
    //        })
    //        self.dataSourceReason(listTypeItem);
            self.bindComment(data);
        }
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
                   if(!res) {
                       
                       return;
                   }
                   self.data = res;
                   self.selectedCode(String(self.data.appRecordImage.appStampCombinationAtr));
                   self.time(Number(self.data.appRecordImage.attendanceTime));
                   if (self.data.appRecordImage.appStampGoOutAtr) {
                       self.selectedCodeReason(String(self.data.appRecordImage.appStampGoOutAtr));
                   }
                   self.bindDataStart(self.data);
                   self.printContentOfEachAppDto().opAppStampOutput = res;
                   
               }).fail(res => {
                   console.log('fail');
               }).always(() => {
                   self.$blockui('hide');
               });
       }
       bindComment(data: any) {
           const self = this;
           _.forEach(self.data.appStampSetting.settingForEachTypeLst, i => {
              if (i.stampAtr == 6) {
                  let commentBot = i.bottomComment;
                  self.comment2(new Comment(commentBot.comment, commentBot.bold, commentBot.colorCode));
                  let commentTop = i.topComment;
                  self.comment1(new Comment(commentTop.comment, commentTop.bold, commentTop.colorCode));
              }
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
           self.printContentOfEachAppDto = ko.observable(params.printContentOfEachAppDto);
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
           
           self.selectedCode = ko.observable('0');
           
          
           
           self.selectedCodeReason = ko.observable('0');
           
           // initial time 
           self.time = ko.observable(null);
           
           params.eventUpdate(self.update.bind(self));
           
           self.fetchData();
           
       }
       
       public handleConfirmMessage(listMes: any, res: any) {
           let self = this;
           if (!_.isEmpty(listMes)) {
               let item = listMes.shift();
               self.$dialog.confirm({ messageId: item.msgID }).then((value) => {
                   if (value == 'yes') {
                       if (_.isEmpty(listMes)) {
                            return self.registerData(res);
                       } else {
                            self.handleConfirmMessage(listMes, res);
                       }

                   }
               });
           }
       }
       registerData(command) {
           let vm = this; 
           return vm.$ajax( API.update, command )
               .done( update => {
                   this.$dialog.info( { messageId: "Msg_15" } ).then(() => {
                       location.reload();
                   } );
               })
       }
       update() {
           console.log('update');
           const self = this;
           if (!self.appDispInfoStartupOutput().appDetailScreenInfo) {
               return;
           }
//           let application = ko.toJS(self.application);
//           let applicationDto = self.appDispInfoStartupOutput().appDetailScreenInfo.application as any;
//           applicationDto.prePostAtr = application.prePostAtr;
//           applicationDto.opAppReason = application.opAppReason;
//           applicationDto.opAppStandardReasonCD = application.opAppStandardReasonCD;    
//           applicationDto.opReversionReason = application.opReversionReason;
//           applicationDto.enteredPerson = application.enteredPerson;
//           applicationDto.employeeID = application.employeeID;
           self.application().enteredPerson = __viewContext.user.employeeId;
           self.application().employeeID = __viewContext.user.employeeId;
           let applicationDto = ko.toJS(self.application);
           applicationDto.inputDate = moment(new Date()).format('YYYY/MM/DD HH:mm:ss');
           applicationDto.reflectionStatus= self.appDispInfoStartupOutput().appDetailScreenInfo.application.reflectionStatus;
           applicationDto.version = self.appDispInfoStartupOutput().appDetailScreenInfo.application.version
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
           let data = _.clone(self.data);
           let companyId = self.$user.companyId
           let agentAtr = false;
           let commandCheck = {
                   companyId,
                   agentAtr,
                   appStampOutputDto: data,
                   applicationDto: applicationDto
           }
           self.$blockui("show");
           self.$validate('.nts-input', '#kaf000-a-component3-prePost', '#kaf000-a-component5-comboReason', '#inputTimeKAF002')
           .then(isValid => {
               if ( isValid ) {
                   return true;
               }
           }).then(result => {
               if (result) {
                   return self.$ajax(API.checkUpdate, commandCheck);
               }
           }).then(res => {
               if (!res) {
                   return;
               }
               if (_.isEmpty(res)) {
                   return self.$ajax(API.update, command);
               } else {
                   let listConfirm = _.clone(res);
                   return self.handleConfirmMessage(listConfirm, command);
               }
           }).then(res => {
                   this.$dialog.info( { messageId: "Msg_15" } ).then(() => {
                       location.reload();
                   } );
           }).fail(res => {
               if (!res) return;
               let param;
               if (res.message && res.messageId) {
                   param = {messageId: res.messageId, messageParams: res.parameterIds};
               } else {

                   if (res.message) {
                       param = {message: res.message, messageParams: res.parameterIds};
                   } else {
                       param = {messageId: res.messageId, messageParams: res.parameterIds};
                   }
               }
               self.$dialog.error(param);
           }).always(() => {
               self.$blockui('hide');
           });
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
    
    class Comment{
        public content: string;
        public isBold: boolean;
        public color: string;
        constructor( content: string, isBold: boolean, color: string) {
            this.content = content;
            this.isBold = isBold;
            this.color = color;
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
    const API = {
            getDetail: "at/request/application/stamp/detailAppStamp",
            update: "at/request/application/stamp/updateNew",
            checkUpdate: "at/request/application/stamp/checkBeforeUpdate",
            checkRegister: "at/request/application/stamp/checkBeforeRegister",
            
        }
}