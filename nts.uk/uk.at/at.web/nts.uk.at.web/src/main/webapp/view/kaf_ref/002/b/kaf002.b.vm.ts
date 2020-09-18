module nts.uk.at.view.kaf002_ref.b.viewmodel {
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import AppType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.AppType;
    import Kaf000AViewModel = nts.uk.at.view.kaf000_ref.a.viewmodel.Kaf000AViewModel;
    @bean()
    class Kaf002BViewModel extends Kaf000AViewModel {
		appType: KnockoutObservable<number> = ko.observable(AppType.STAMP_APPLICATION);
        dataSource: KnockoutObservableArray<ItemModel>;
        dataSourceReason: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        selectedCodeReason: KnockoutObservable<string>;
        time: KnockoutObservable<number>;
        application: KnockoutObservable<Application>;
        isSendMail: KnockoutObservable<boolean> = ko.observable(false);
        data: any;
        comment1: KnockoutObservable<Comment> = ko.observable(new Comment('', true, ''));
        comment2: KnockoutObservable<Comment> = ko.observable(new Comment('', true, ''));
        bindComment(data: any) {
            const self = this;
            _.forEach(self.data.appStampSetting.settingForEachTypeLst, i => {
               if (i.stampAtr == ko.toJS(self.selectedCode)) {
                   let commentBot = i.bottomComment;
                   self.comment2(new Comment(commentBot.comment, commentBot.bold, commentBot.colorCode));
                   let commentTop = i.topComment;
                   self.comment1(new Comment(commentTop.comment, commentTop.bold, commentTop.colorCode));
               }
            });
        }
        created() {
            
            const self = this;
            
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
            self.selectedCode.subscribe(value => {
               if (value) {
                   self.bindComment(self.data);
               } 
            });
            self.selectedCodeReason = ko.observable('0');
            
            // initial time 
            self.time = ko.observable(null);
            
            self.application = ko.observable(new Application(self.appType()));

            self.loadData([], [], self.appType())
            .then((loadDataFlag: any) => {
                if(loadDataFlag) {
                    let companyId = self.$user.companyId;
                    let command = { 
                            appDispInfoStartupDto: ko.toJS(self.appDispInfoStartupOutput),
                            recoderFlag: RECORD_FLAG_IMAGE,
                            companyId
                    };
                
                    return self.$ajax(API.start, command);
                }
            }).done((res: any) => {
                self.data = res;
                self.bindDataStart(self.data);
                
            }).fail(res => {
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
        
        mounted() {
            
        }
        public bindDataStart(data: any) {
            const self = this;
//            let listType = self.data.appStampSetting.goOutTypeDispControl;
//            let listTypeItem = [];
//            _.forEach(listType, i => {
//                listTypeItem.push(new ItemModel(String(i.goOutType), i.display))
//            })
//            self.dataSourceReason(listTypeItem);
            self.bindComment(data);
        }
        public changeDate() {
            const self = this;
            let dataClone = _.clone(self.data);
            if (_.isNull(dataClone)) {
                return;
            }
            self.$blockui( "show" );
            let companyId = self.$user.companyId;
            let command = { 
                    appDispInfoStartupDto: ko.toJS(self.appDispInfoStartupOutput),
                    recoderFlag: RECORD_FLAG_IMAGE,
                    companyId
            };
            self.$ajax(API.start, command)
                .done((res: any) => {
                    console.log(res);
                    self.data = res;
                }).fail(res => {
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
        public handleConfirmMessage(listMes: any, res: any) {
            let vm = this;
            if (!_.isEmpty(listMes)) {
                let item = listMes.shift();
                vm.$dialog.confirm({ messageId: item.msgID }).then((value) => {
                    if (value == 'yes') {
                        if (_.isEmpty(listMes)) {
                             return vm.registerData(res);
                        } else {
                             vm.handleConfirmMessage(listMes, res);
                        }

                    }
                });
            }
        }
        registerData(command) {
            let vm = this; 
            return vm.$ajax( API.register, command )
                .done( resRegister => {
                    console.log( resRegister );
                    this.$dialog.info( { messageId: "Msg_15" } ).then(() => {
                        location.reload();
                    } );
                })
        }
        public register() {
            console.log('register');
            const self = this;
            let data = _.clone(self.data);
            let appRecordImage = new AppRecordImage(Number(ko.toJS(self.selectedCode)), Number(ko.toJS(self.time)));
            if (ko.toJS(self.selectedCode) == '3') {
                appRecordImage.appStampGoOutAtr = Number(ko.toJS(self.selectedCodeReason));
            }
            data.appRecordImage = null;
            let companyId = self.$user.companyId;
            let agentAtr = false;
            self.application().enteredPerson = __viewContext.user.employeeId;
            self.application().employeeID = __viewContext.user.employeeId;
//            self.application().prePostAtr(0);
            let command = {
                    appStampOutputDto: data,
                    applicationDto: ko.toJS(self.application),
                    recoderFlag: RECORD_FLAG_IMAGE,
                    appRecordImageDto: appRecordImage
                    
            };
            let commandCheck = {
                    companyId,
                    agentAtr,
                    appStampOutputDto: data,
                    applicationDto: ko.toJS(self.application)
            }
            self.$blockui("show");
            self.$validate('.nts-input', '#kaf000-a-component3-prePost', '#kaf000-a-component5-comboReason', '#inputTimeKAF002')
            .then(isValid => {
                if ( isValid ) {
                    return true;
                }
            }).then(result => {
                if (result) {
                    return self.$ajax(API.checkRegister, commandCheck);
                } 
            }).then(res => {
                if (!res) return;
                if (_.isEmpty(res)) {
                    return self.$ajax(API.register, command);
                } else {
                    let listConfirm = _.clone(res);
                    return self.handleConfirmMessage(listConfirm, command);
                }
            }).done(res => {
                if (res) {
                    this.$dialog.info( { messageId: "Msg_15" } ).then(() => {
                        location.reload();
                    } );
                }
            }).fail(res => {
                if (!res) {
                    
                    return;
                }
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
    class AppRecordImage {
        appStampCombinationAtr: number;
        attendanceTime: number;
        appStampGoOutAtr?: number;
        constructor(appStampCombinationAtr: number, attendanceTime: number, appStampGoOutAtr?: number) {
            this.appStampCombinationAtr = appStampCombinationAtr;
            this.attendanceTime = attendanceTime;
            this.appStampGoOutAtr = appStampGoOutAtr;
        }
    }
    export class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
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
    const RECORD_FLAG_IMAGE = true;
    const API = {
            start: "at/request/application/stamp/startStampApp",
            checkRegister: "at/request/application/stamp/checkBeforeRegister",
            register: "at/request/application/stamp/register",
            getDetail: "at/request/application/stamp/detailAppStamp"
            
        }
}