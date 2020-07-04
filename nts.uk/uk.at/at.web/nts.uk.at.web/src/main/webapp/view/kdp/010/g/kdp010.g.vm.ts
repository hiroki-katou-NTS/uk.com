module nts.uk.at.view.kdp010.g {
	export module viewmodel {
        import getShared = nts.uk.ui.windows.getShared;
        import block = nts.uk.ui.block;
        import info = nts.uk.ui.dialog.info;
        import error = nts.uk.ui.dialog.error;
		export class ScreenModel {
			// G2_2
			optionPage: KnockoutObservableArray<any> = ko.observableArray([
				{ code: 1, name: nts.uk.resource.getText("KDP010_98", "1") },
				{ code: 2, name: nts.uk.resource.getText("KDP010_98", "2") },
				{ code: 3, name: nts.uk.resource.getText("KDP010_98", "3") },
				{ code: 4, name: nts.uk.resource.getText("KDP010_98", "4") },
				{ code: 5, name: nts.uk.resource.getText("KDP010_98", "5") }]);
			selectedPage: KnockoutObservable<number> = ko.observable(1);
			// G3_2
			pageName: KnockoutObservable<string> = ko.observable("");
			// G4_2
			optionLayout: KnockoutObservableArray<any> = ko.observableArray(__viewContext.enums.ButtonLayoutType);
			selectedLayout: KnockoutObservable<number> = ko.observable(0);
			// G5_2
			commentDaily: KnockoutObservable<string> = ko.observable("");
			// G6_2
			letterColors: KnockoutObservable<string> = ko.observable("#000000");
			dataKdpH: KnockoutObservable<model.StampPageCommentCommand> = ko.observable(new model.StampPageCommentCommand({}));
			dataShare: KnockoutObservableArray<any> = ko.observableArray([]);
			isDel: KnockoutObservable<string> = ko.observable(false);
			buttonInfo: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);
			checkDelG: KnockoutObservable<string> = ko.observable(false);
			checkLayout: KnockoutObservable<string> = ko.observable(false);
			currentSelectLayout: KnockoutObservable<number> = ko.observable(0);
            
             /**
             * 運用方法 (0:共有打刻 1:個人利用 2:ICカード 3:スマホ打刻)
             * 0: Shared stamp, 1: Personal use, 2: IC card, 3: smartphone engraving
             */
            mode: number;

			constructor() {
				let self = this;
                self.mode = getShared('STAMP_MEANS');
                
				self.selectedLayout.subscribe((newValue) => {
					self.checkDelG(true);
					self.getData(newValue);
					self.checkLayout(true);
					nts.uk.ui.errors.clearAll();
				})

				self.pageName.subscribe(function(codeChanged: string) {
					self.pageName($.trim(self.pageName()));
				});
				

				self.selectedPage.subscribe((newValue) => {
					nts.uk.ui.block.grayout();
					//setTimeout(function(){ 
					self.checkLayout(false);
					self.startPage();
					nts.uk.ui.errors.clearAll();
					nts.uk.ui.block.clear();
					 //}, 500);
					
				})
			}
            /**
             * start page  
             */
			public startPage(): JQueryPromise<any> {
				let self = this,
					dfd = $.Deferred();
				self.getData(self.selectedLayout());
				let lstButton: model.ItemModel[] = [];
				for (let i = 1; i < 9; i++) {
					lstButton.push(new model.ItemModel(self.selectedLayout(), '', '', ''));
				}
				self.buttonInfo(lstButton);
				dfd.resolve();
				return dfd.promise();
			}
			getData(newValue: number): JQueryPromise<any> {
				let self = this;
				let dfd = $.Deferred();
                let param = {mode: self.mode, pageNo: self.selectedPage()};
				service.getStampPage(param).done(function(totalTimeArr) {

					if (totalTimeArr && (newValue == totalTimeArr.buttonLayoutType)) {
						self.pageName(totalTimeArr.stampPageName);
						self.commentDaily(totalTimeArr.stampPageComment.pageComment);
						self.letterColors(totalTimeArr.stampPageComment.commentColor);
						if (totalTimeArr.lstButtonSet != null) {
							self.getInfoButton(totalTimeArr.lstButtonSet, totalTimeArr.buttonLayoutType);
						}
						self.dataShare = totalTimeArr;
						self.isDel(true);
					} else {
						self.setColor("#999", ".btn-name");
						self.getInfoButton(null, self.selectedLayout());
						self.isDel(false);
						if (self.checkLayout() == false) {
							self.pageName("");
							self.commentDaily("");
							self.letterColors("#000000");
						}
						self.dataShare = [];
					}

					if (totalTimeArr) {
						if (self.checkLayout() == false)
							self.selectedLayout(totalTimeArr.buttonLayoutType);
						else
							self.selectedLayout(newValue);
					} else {
						if (self.checkLayout() == false)
							self.selectedLayout(0);
						else
							self.selectedLayout(newValue);
					}
					$('#combobox').focus();
					dfd.resolve();
				}).fail(function(error) {
					alert(error.message);
					dfd.reject(error);
				});
				return dfd.promise();
			}

			registration() {
				let self = this, dfd = $.Deferred();
				if (!self.dataShare || self.dataShare.length == 0) {
					error({ messageId: "Msg_1627" });
					return;
				}
				if (self.checkDelG() == true) {
					$.when(self.deleteBeforeAdd()).done(function() {
						self.registrationLayout();
						$(document).ready(function() {
							$('#combobox').focus();
						});
						dfd.resolve();
					});
				} else {
					self.registrationLayout();
					dfd.resolve();
				}
				return dfd.promise();

			}

            /**
             * Pass Data to A
             */
			public registrationLayout() {
				let self = this;
				$('#correc-text').trigger("validate");
				if (nts.uk.ui.errors.hasError()) {
					return;
				}
				nts.uk.ui.block.invisible();
				if ((self.dataKdpH == undefined || self.dataKdpH.length == 0) && self.isDel() == true) {
					let data = {
						dataShare: self.dataShare,
						buttonPositionNo: self.dataShare.lstButtonSet[0].buttonPositionNo
					}
					self.dataKdpH = data;
				}
				if ((self.dataKdpH == undefined) && self.isDel() == false) {
					let data = {
						dataShare: [],
						buttonPositionNo: self.selectedLayout()
					}
					self.dataKdpH = data;
				}
				
				if(!_.isNil(self.dataKdpH) && self.dataKdpH.dataShare.pageNo != self.dataShare.pageNo){
					let data = {
						dataShare: self.dataShare,
						buttonPositionNo: self.dataShare.lstButtonSet[0].buttonPositionNo
					}
					self.dataKdpH = data;
				}
				// Data from Screen 
				let lstButton = new Array<model.ButtonSettingsCommand>();
				let stampTypes = null;
				if (self.dataKdpH.buttonPositionNo != undefined) {
					let lstButtonSet = new Array<>();
					_.forEach(self.dataKdpH.dataShare.lstButtonSet, (item) => {
						if (item.buttonType.reservationArt == 0) {
							stampTypes = new model.StampTypeCommand({
								changeHalfDay: item.buttonType.stampType.changeHalfDay == 0 ? false : true,
								goOutArt: item.buttonType.stampType.goOutArt,
								setPreClockArt: item.buttonType.stampType.setPreClockArt,
								changeClockArt: item.buttonType.stampType.changeClockArt,
								changeCalArt: item.buttonType.stampType.changeCalArt
							});
							
						} else {
							stampTypes = null
						};
						lstButtonSet = new model.ButtonSettingsCommand({
							buttonPositionNo: item.buttonPositionNo,
							buttonDisSet: new model.ButtonDisSetCommand({
								buttonNameSet: new model.ButtonNameSetCommand({
									textColor: item.buttonDisSet.buttonNameSet.textColor,
									buttonName: item.buttonDisSet.buttonNameSet.buttonName
								}),
								backGroundColor: item.buttonDisSet.backGroundColor
							}),
							buttonType: new model.ButtonTypeCommand({
								reservationArt: item.buttonType.reservationArt,
								stampType: stampTypes
							}),
							usrArt: item.usrArt,
							audioType: item.audioType
						});
						lstButton.push(lstButtonSet);
					});
				}

				let data = new model.StampPageLayoutCommand({
					pageNo: self.selectedPage(),
					stampPageName: self.pageName(),
					stampPageComment: new model.StampPageCommentCommand({
						pageComment: self.commentDaily(),
						commentColor: self.letterColors(),
					}),
					buttonLayoutType: self.selectedLayout(),
					lstButtonSet: lstButton
				});
                if(self.mode == 1){
    				service.saveStampPage(data).done(function() {
    					self.isDel(true);
    					self.checkDelG(false);
    					self.currentSelectLayout(self.selectedLayout());
    					info({ messageId: "Msg_15" }).then(() => {
    						$(document).ready(function() {
    							$('#combobox').focus();
    						});
    					});
    
    				}).fail(function(res) {
    					error({ messageId: res.messageId });
    				}).always(() => {
    					block.clear();
    				});
                }else if(self.mode == 0){
                    self.saveStampPageCommunal(data);
                }
			}
            
            saveStampPageCommunal(data: any){
                let self = this;
                service.saveStampPageCommunal(data).done(function() {
                    self.isDel(true);
                    self.checkDelG(false);
                    self.currentSelectLayout(self.selectedLayout());
                    info({ messageId: "Msg_15" }).then(() => {
                        $(document).ready(function() {
                            $('#combobox').focus();
                        });
                    });

                }).fail(function(res) {
                    error({ messageId: res.messageId });
                }).always(() => {
                    block.clear();
                });    
            }

			public deleteBeforeAdd() {
				let self = this, dfd = $.Deferred();
                if(self.mode == 1){
    				let data = {
    					pageNo: self.selectedPage(),
                        mode: self.mode
    				};
    
    				service.deleteStampPage(data).done(function(stampPage) {
                        dfd.resolve();
    				}).fail(function(error) {
    					alert(error.message);
    					dfd.reject(error);
    				});
                }
			}

			public getInfoButton(lstButtonSet: any, buttonLayoutType: number) {
				let self = this, buttonLength = 0;
				if (lstButtonSet == null) {
					for (let i = 0; i < 8; i++) {
						self.buttonInfo()[i].buttonColor("#999");
						self.buttonInfo()[i].buttonName(null);
						self.buttonInfo()[i].textColor("#999");
					}
				} else {
					for (let i = 0; i < 8; i++) {
						self.buttonInfo()[i].buttonName(lstButtonSet.filter(x => x.buttonPositionNo == i + 1)[0] == null ? "" : lstButtonSet.filter(x => x.buttonPositionNo == i + 1)[0].buttonDisSet.buttonNameSet.buttonName);
						let lstBtn = lstButtonSet.filter(x => x.buttonPositionNo == i + 1);
						if (lstBtn[0] == null || (lstBtn[0] != null && lstBtn[0].usrArt == 0)) {
							self.buttonInfo()[i].buttonColor("#999");
							self.buttonInfo()[i].buttonName(null);
							self.buttonInfo()[i].textColor("#999");
						} else {
							self.buttonInfo()[i].buttonColor((lstButtonSet.filter(x => x.buttonPositionNo == i + 1)[0].buttonDisSet.backGroundColor));
							self.buttonInfo()[i].buttonName(lstButtonSet.filter(x => x.buttonPositionNo == i + 1)[0].buttonDisSet.buttonNameSet.buttonName);
							self.buttonInfo()[i].textColor((lstButtonSet.filter(x => x.buttonPositionNo == i + 1)[0].buttonDisSet.buttonNameSet.textColor));
						}
					}
				}

			}

			public setColor(color: string, name: string) {
				$(name).css("background", color);
			}

			public deleteStamp(): JQueryPromise<any> {
				let self = this;
				let dfd = $.Deferred();
				let data = {
					pageNo: self.selectedPage(),
                    mode: self.mode
				};
				nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
					service.deleteStampPage(data).done(function(stampPage) {
						info({ messageId: "Msg_16" }).then(() => {
							$(document).ready(function() {
								$('#combobox').focus();
							});
						});
						self.getData(self.selectedLayout());
						dfd.resolve();
					}).fail(function(error) {
						alert(error.message);
						dfd.reject(error);
					});
				}).ifNo(function() {
					block.clear();
				});
				return dfd.promise();
			}

            /**
             * Close dialog
             */
			public closeDialog(): void {
				nts.uk.ui.windows.close();
			}

			openHDialog(enumVal: number, data): void {
				let self = this;
				let shareH = {
					pageNo: self.selectedPage(),
					stampPageName: self.pageName(),
					stampPageComment: {
						pageComment: self.commentDaily(),
						commentColor: self.letterColors(),
					},

					buttonLayoutType: self.selectedLayout(),
					lstButtonSet: []
				};
				let dataG = {
					dataShare: self.dataShare.length == 0 ? shareH : self.dataShare,
					buttonPositionNo: enumVal
				}
				nts.uk.ui.windows.setShared('KDP010_G', dataG);
				nts.uk.ui.windows.sub.modal("/view/kdp/010/h/index.xhtml").onClosed(() => {
					self.dataKdpH = nts.uk.ui.windows.getShared('KDP010_H');
					if (self.dataKdpH) {
						self.dataShare = self.dataKdpH.dataShare == undefined ? self.dataKdpH : self.dataKdpH.dataShare;
						if (self.dataKdpH.dataShare) {
							let dataH = self.dataKdpH.dataShare.lstButtonSet.filter(x => x.buttonPositionNo == self.dataKdpH.buttonPositionNo)[0];
							if (dataH.usrArt == 0) {
								self.buttonInfo()[self.dataKdpH.buttonPositionNo - 1].buttonColor("#999");
								self.buttonInfo()[self.dataKdpH.buttonPositionNo - 1].textColor("#999");
								return;
							}

						}

						self.buttonInfo()[self.dataKdpH.buttonPositionNo - 1].buttonName(dataH ? dataH.buttonDisSet.buttonNameSet.buttonName : self.dataKdpH.buttonDisSet.buttonNameSet.buttonName);
						self.buttonInfo()[self.dataKdpH.buttonPositionNo - 1].buttonColor(dataH ? dataH.buttonDisSet.backGroundColor : self.dataKdpH.buttonDisSet.backGroundColor);
						self.buttonInfo()[self.dataKdpH.buttonPositionNo - 1].textColor(dataH ? dataH.buttonDisSet.buttonNameSet.textColor : self.dataKdpH.buttonDisSet.buttonNameSet.textColor);
					}
				});
			}
		}

	}
	export module model {

		export class ButtonInfo {
			buttonName: KnockoutObservable<string>;
			buttonColor: KnockoutObservable<string>;
			textColor: KnockoutObservable<string>;

			constructor(param: IButtonInfo) {
				this.buttonName = ko.observable(param.buttonName) || ko.observable('');
				this.buttonColor = ko.observable(param.buttonColor) || '';
				this.textColor = ko.observable(param.textColor) || '';

			}
		}

		interface IButtonInfo {
			buttonName: string;
			buttonColor: string;
			textColor: string;
		}


		// StampPageLayoutCommand
		export class StampPageLayoutCommand {

			/** ページNO */
			pageNo: number;
			/** ページ名 */
			stampPageName: string;
			/** ページコメント */
			stampPageComment: StampPageCommentCommand;
			/** ボタン配置タイプ */
			buttonLayoutType: number;
			/** ボタン詳細設定リスト */
			lstButtonSet: Array<ButtonSettingsCommand>;

			constructor(param: IStampPageLayoutCommand) {
				this.pageNo = param.pageNo;
				this.stampPageName = param.stampPageName;
				this.stampPageComment = param.stampPageComment;
				this.buttonLayoutType = param.buttonLayoutType;
				this.lstButtonSet = param.lstButtonSet;
			}
		}

		interface IStampPageLayoutCommand {
			pageNo: number;
			stampPageName: string;
			stampPageComment: StampPageCommentCommand;
			buttonLayoutType: number;
			lstButtonSet: Array<ButtonSettingsCommand>;
		}

		// StampPageCommentCommand
		export class StampPageCommentCommand {

			/** ページNO */
			pageComment: string;
			/** ページ名 */
			commentColor: string;

			constructor(param: IStampPageCommentCommand) {
				this.pageComment = param.pageComment;
				this.commentColor = param.commentColor;
			}
		}

		interface IStampPageCommentCommand {
			pageComment: string;
			commentColor: string;
		}

		// ButtonSettingsCommand
		export class ButtonSettingsCommand {
			/** ボタン位置NO */
			buttonPositionNo: number;
			/** ボタンの表示設定 */
			buttonDisSet: ButtonDisSetCommand;
			/** ボタン種類 */
			buttonType: ButtonTypeCommand;
			/** 使用区分 */
			usrArt: number;
			/** 音声使用方法 */
			audioType: number;

			constructor(param: IButtonSettingsCommand) {
				this.buttonPositionNo = param.buttonPositionNo;
				this.buttonDisSet = param.buttonDisSet;
				this.buttonType = param.buttonType;
				this.usrArt = param.usrArt;
				this.audioType = param.audioType;
			}
		}

		interface IButtonSettingsCommand {
			/** ボタン位置NO */
			buttonPositionNo: number;
			/** ボタンの表示設定 */
			buttonDisSet: ButtonDisSetCommand;
			/** ボタン種類 */
			buttonType: ButtonTypeCommand;
			/** 使用区分 */
			usrArt: number;
			/** 音声使用方法 */
			audioType: number;
		}

		// ButtonDisSetCommand
		export class ButtonDisSetCommand {

			/** ボタン名称設定 */
			buttonNameSet: ButtonNameSetCommand;
			/** 背景色 */
			backGroundColor: string;

			constructor(param: IButtonDisSetCommand) {
				this.buttonNameSet = param.buttonNameSet;
				this.backGroundColor = param.backGroundColor;
			}
		}

		interface IButtonDisSetCommand {
			buttonNameSet: ButtonNameSetCommand;
			backGroundColor: string;
		}

		// ButtonNameSetCommand
		export class ButtonNameSetCommand {

			/** ボタン名称設定 */
			textColor: string;
			/** 背景色 */
			buttonName: string;

			constructor(param: IButtonNameSetCommand) {
				this.textColor = param.textColor;
				this.buttonName = param.buttonName;
			}
		}

		interface IButtonNameSetCommand {
			textColor: string;
			buttonName: string;
		}

		// ButtonTypeCommand
		export class ButtonTypeCommand {

			/** 予約区分 */
			reservationArt: number;
			/** 打刻種類 */
			stampType: StampTypeCommand;

			constructor(param: IButtonTypeCommand) {
				this.reservationArt = param.reservationArt;
				this.stampType = param.stampType;
			}
		}

		interface IButtonTypeCommand {
			reservationArt: number;
			stampType: StampTypeCommand;
		}

		// StampTypeCommand
		export class StampTypeCommand {

			/** 勤務種類を半休に変更する */
			changeHalfDay: boolean;
			/** 外出区分 */
			goOutArt: number;
			/** 所定時刻セット区分 */
			setPreClockArt: number;
			/** 時刻変更区分 */
			changeClockArt: number;
			/** 計算区分変更対象 */
			changeCalArt: number;

			constructor(param: IStampTypeCommand) {
				this.changeHalfDay = param.changeHalfDay;
				this.goOutArt = param.goOutArt;
				this.setPreClockArt = param.setPreClockArt;
				this.changeClockArt = param.changeClockArt;
				this.changeCalArt = param.changeCalArt;
			}
		}

		interface IStampTypeCommand {
			changeHalfDay: boolean;
			goOutArt: number;
			setPreClockArt: number;
			changeClockArt: number;
			changeCalArt: number;
		}

		export enum ButtonLayoutType {
			//就業
			大2小4 = 0,
			小8 = 1
		}
		export class ItemModel {
			buttonName: string;
			buttonColor: string;
			textColor: string;

			constructor(buttonName: string, buttonColor: string, textColor: string) {
				this.buttonName = ko.observable('') || '';
				this.buttonColor = ko.observable('') || '';
				this.textColor = ko.observable('') || '';
			}
		}
	}
}