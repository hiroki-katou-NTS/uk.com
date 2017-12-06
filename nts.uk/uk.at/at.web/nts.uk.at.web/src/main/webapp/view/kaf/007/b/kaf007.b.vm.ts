module nts.uk.at.view.kaf007.b {
    import common = nts.uk.at.view.kaf007.share.common;
    import service = nts.uk.at.view.kaf007.share.service;
    import dialog = nts.uk.ui.dialog;
    import model = nts.uk.at.view.kaf000.b.viewmodel.model;
    import appcommon = nts.uk.at.view.kaf000.shr.model;
    export module viewmodel {
        export class ScreenModel extends kaf000.b.viewmodel.ScreenModel {

            //kaf000
            //kaf000_a: kaf000.a.viewmodel.ScreenModel;
            appWorkChange: KnockoutObservable<common.AppWorkChangeCommand> = ko.observable( new common.AppWorkChangeCommand() );
            //A3 事前事後区分:表示/活性
            prePostDisp: KnockoutObservable<boolean> = ko.observable( false );
            prePostEnable: KnockoutObservable<boolean> = ko.observable( false );
            //A5 勤務を変更する:表示/活性
            //Show/Hide work change
            isWorkChange: KnockoutObservable<boolean> = ko.observable( true );
            workChangeAtr: KnockoutObservable<boolean> = ko.observable( false );
            workState: KnockoutObservable<boolean> = ko.observable( true );
            typeSiftVisible: KnockoutObservable<boolean> = ko.observable( true );
            //kaf000
            kaf000_a: kaf000.a.viewmodel.ScreenModel;
            //申請者
            employeeName: KnockoutObservable<string> = ko.observable( "" );

            //勤務種類
            workTypeCd: KnockoutObservable<string> = ko.observable( '' );
            workTypeName: KnockoutObservable<string> = ko.observable( '' );
            //勤務種類
            siftCD: KnockoutObservable<string> = ko.observable( '' );
            siftName: KnockoutObservable<string> = ko.observable( '' );

            //A8 勤務時間２
            isMultipleTime: KnockoutObservable<boolean> = ko.observable( false );

            //comboBox 定型理由
            typicalReasonDisplayFlg: KnockoutObservable<boolean> = ko.observable(false);
            displayAppReasonContentFlg: KnockoutObservable<boolean> = ko.observable(false);
            reasonCombo: KnockoutObservableArray<common.ComboReason> = ko.observableArray( [] );
            selectedReason: KnockoutObservable<string> = ko.observable( '' );
            //MultilineEditor
            requiredReason: KnockoutObservable<boolean> = ko.observable( false );
            multilContent: KnockoutObservable<string> = ko.observable( '' );
            //excludeHolidayAtr
            excludeHolidayAtr: KnockoutObservable<boolean> = ko.observable( false );

            //Approval 
            approvalSource: Array<common.AppApprovalPhase> = [];
            employeeID: string = "";
            //menu-bar 
            enableSendMail: KnockoutObservable<boolean> = ko.observable( false );

            dateFormat: string = 'YYYY/MM/DD';
            dateTimeFormat: string = 'YYYY/MM/DD hh:mm:ss';
            constructor( listAppMetadata: Array<model.ApplicationMetadata>, currentApp: model.ApplicationMetadata ) {
                super( listAppMetadata, currentApp );
                let self = this;
                self.startPage( self.appID() );
            }
            /**
             * 起動する
             */
            startPage( appID: string ): JQueryPromise<any> {
                nts.uk.ui.block.invisible();
                var self = this;
                let dfd = $.Deferred();
                //get Common Setting
                service.getWorkChangeCommonSetting().done( function( settingData: any ) {
                    if ( !nts.uk.util.isNullOrEmpty( settingData ) ) {
                        //申請共通設定
                        let appCommonSettingDto = settingData.appCommonSettingDto;
                        //勤務変更申請設定
                        let appWorkChangeCommonSetting = settingData.workChangeSetDto;

                        //A2_申請者 ID
                        self.employeeID = settingData.sid;
                        //A2_1 申請者
                        self.employeeName( settingData.employeeName );

                        //A3 事前事後区分
                        //事前事後区分 ※A１
                        self.prePostDisp( appCommonSettingDto.applicationSettingDto.displayPrePostFlg == 1 ? true : false );
                        if ( !nts.uk.util.isNullOrEmpty( appCommonSettingDto.appTypeDiscreteSettingDtos ) &&
                            appCommonSettingDto.appTypeDiscreteSettingDtos.length > 0 ) {
                            //事前事後区分 Enable ※A２
                            self.prePostEnable( appCommonSettingDto.appTypeDiscreteSettingDtos[0].prePostCanChangeFlg == 1 ? true : false );
                            //「申請種類別設定．定型理由の表示」 ※A10
                            self.typicalReasonDisplayFlg(appCommonSettingDto.appTypeDiscreteSettingDtos[0].typicalReasonDisplayFlg == 1 ? true : false );
                            //「申請種類別設定．申請理由の表示」 ※A11
                            self.displayAppReasonContentFlg(appCommonSettingDto.appTypeDiscreteSettingDtos[0].displayReasonFlg == 1 ? true : false );
                        }
                        //A5 勤務を変更する ※A4                    
                        if ( appWorkChangeCommonSetting != undefined ) {
                            //条件：勤務変更申請共通設定.勤務の変更　＝　申請時に決める（初期選択：勤務を変更する）
                            self.isWorkChange( true );
                            self.workChangeAtr( true );
                            //条件：勤務変更申請共通設定.勤務の変更　＝　申請時に決める（初期選択：勤務を変更しない）
                            if ( appWorkChangeCommonSetting.workChangeTimeAtr == 0 ) {
                                self.workChangeAtr( false );
                            }
                            //条件：勤務変更申請共通設定.勤務の変更　＝　変更しない 
                            else if ( appWorkChangeCommonSetting.workChangeTimeAtr == 2 ) {
                                self.isWorkChange( false );
                                self.workChangeAtr( false );
                            } else {
                                self.workState( false );
                            }
                        }
                        //定型理由
                        self.setReasonControl( settingData.listReasonDto );
                        //申請制限設定.申請理由が必須
                        self.requiredReason( settingData.appCommonSettingDto.applicationSettingDto.requireAppReasonFlg == 1 ? true : false );
                        //A8 勤務時間２ ※A7
                        //共通設定.複数回勤務
                        self.isMultipleTime( settingData.multipleTime );
                        //勤務変更申請基本データ（更新）
                        service.getWorkchangeByAppID( self.appID() ).done( function( detailData: any ) {
                            //workChangeDto
                            ko.mapping.fromJS( detailData.workChangeDto, {}, self.appWorkChange().workChange );
                            self.workChangeAtr( self.appWorkChange().workChange().workChangeAtr() == 1 ? true : false );
                            self.excludeHolidayAtr(self.appWorkChange().workChange().excludeHolidayAtr() == 1 ? true : false );
                            //application data
                            ko.mapping.fromJS( detailData.applicationDto, {}, self.appWorkChange().application );
                        } ).fail( function() {
                            dfd.resolve();
                        } );
                        dfd.resolve();
                    }
                } );
                return dfd.promise();
            }

            /**
             * 
             */
            update() {
                let self =this;
                nts.uk.ui.block.invisible(); 
                let appReason: string;
                appReason = self.getReason(
                    self.typicalReasonDisplayFlg(),
                    self.selectedReason(),
                    self.reasonCombo(),
                    self.displayAppReasonContentFlg(),
                    self.multilContent()
                );
                let appReasonError = !appcommon.CommonProcess.checkAppReason(true, self.typicalReasonDisplayFlg(), self.displayAppReasonContentFlg(), appReason);
                if(appReasonError){
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_115' }).then(function(){nts.uk.ui.block.clear();});    
                    return;    
                }
                //Approval phase
                self.appWorkChange().appApprovalPhases = self.approvalList;
                //application change date format
                self.changeDateFormat();
                //申請理由
                self.appWorkChange().application().applicationReason(appReason);                
                //勤務を変更する
                self.appWorkChange().workChange().workChangeAtr(self.workChangeAtr() == true ? 1 : 0);
                // 休日に関して
                self.appWorkChange().workChange().excludeHolidayAtr(self.excludeHolidayAtr() == true ? 1 : 0);
                let workChange = ko.toJS(self.appWorkChange());
                service.updateWorkChange(workChange).done(() => {
                    
                    dialog.info({ messageId: "Msg_15" }).then(function() {
                        location.reload();
                    });
                }).fail((res) => {
                    dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                    nts.uk.ui.block.clear();
                });

            }
            private changeDateFormat() {
                let self = this,
                    app = self.appWorkChange().application();
                //Change application input date                
                app.inputDate( moment.utc( app.inputDate(), self.dateTimeFormat ).toISOString() );
                app.applicationDate( moment.utc( app.applicationDate(), self.dateFormat ).toISOString() );
                app.startDate( moment.utc( app.startDate(), self.dateFormat ).toISOString() );
                app.endDate( moment.utc( app.endDate(), self.dateFormat ).toISOString() );
            }
            
            private getReason( inputReasonDisp: boolean, inputReasonID: string, inputReasonList: Array<common.ComboReason>, detailReasonDisp: boolean, detailReason: string ): string {
                let appReason = '';
                let inputReason: string = '';
                if ( !nts.uk.util.isNullOrEmpty( inputReasonID ) ) {
                    inputReason = _.find( inputReasonList, o => { return o.reasonId == inputReasonID; } ).reasonName;
                }
                if ( inputReasonDisp == true && detailReasonDisp == true ) {
                    if ( !nts.uk.util.isNullOrEmpty( inputReason ) && !nts.uk.util.isNullOrEmpty( detailReason ) ) {
                        appReason = inputReason + ":" + detailReason;
                    } else if ( !nts.uk.util.isNullOrEmpty( inputReason ) && nts.uk.util.isNullOrEmpty( detailReason ) ) {
                        appReason = inputReason;
                    } else if ( nts.uk.util.isNullOrEmpty( inputReason ) && !nts.uk.util.isNullOrEmpty( detailReason ) ) {
                        appReason = detailReason;
                    }
                } else if ( inputReasonDisp == true && detailReasonDisp == false ) {
                    appReason = inputReason;
                } else if ( inputReasonDisp == false && detailReasonDisp == true ) {
                    appReason = detailReason;
                }
                return appReason;
            }
            /**
             * set reason 
             */
            setReasonControl( data: Array<common.ReasonDto> ) {
                var self = this;
                let comboSource: Array<common.ComboReason> = [];
                _.forEach( data, function( value: common.ReasonDto ) {
                    self.reasonCombo.push( new common.ComboReason( value.displayOrder, value.reasonTemp, value.reasonID ) );
                    if ( value.defaultFlg === 1 ) {
                        self.selectedReason( value.reasonID );
                    }
                } );
            }

            /**
             * 「勤務就業選択」ボタンをクリックする
             * KDL003_勤務就業ダイアログを起動する
             */
            openKDL003Click() {
                let self = this,
                    workTypeCodes = [],
                    workChange = self.workChange(),
                    workTimeCodes = [];
                nts.uk.ui.windows.setShared( 'parentCodes', {
                    workTypeCodes: workTypeCodes,
                    selectedWorkTypeCode: workChange.workTypeCd(),
                    workTimeCodes: workTimeCodes,
                    selectedWorkTimeCode: workChange.workTimeCd(),
                }, true );

                nts.uk.ui.windows.sub.modal( '/view/kdl/003/a/index.xhtml' ).onClosed( function(): any {
                    //view all code of selected item 
                    var childData = nts.uk.ui.windows.getShared( 'childData' );
                    if ( childData ) {

                        workChange.workTypeCd( childData.selectedWorkTypeCode );
                        workChange.workTypeName( childData.selectedWorkTypeName );
                        workChange.workTimeCd( childData.selectedWorkTimeCode );
                        workChange.workTimeName( childData.selectedWorkTimeName );
                    }
                } );
            }
        }
    }
}