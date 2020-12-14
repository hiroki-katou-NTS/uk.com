module cmm045.a.viewmodel {
    import vmbase = cmm045.shr.vmbase;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import character = nts.uk.characteristics;
    import request = nts.uk.request;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        roundingRules: KnockoutObservableArray<vmbase.ApplicationDisplayAtr> = ko.observableArray([]);
        //delete switch button - ver35
//        selectedRuleCode: KnockoutObservable<any> = ko.observable(0);// switch button
        //lst fill in grid list
        items: KnockoutObservableArray<vmbase.DataModeApp> = ko.observableArray([]);
        //lst full data get from db
        lstApp: KnockoutObservableArray<vmbase.DataModeApp> = ko.observableArray([]);
        lstAppCommon: KnockoutObservableArray<vmbase.ApplicationDataOutput> = ko.observableArray([]);
        lstAppMaster: KnockoutObservableArray<vmbase.AppMasterInfo> = ko.observableArray([]);
        lstListAgent: KnockoutObservableArray<vmbase.ApproveAgent> = ko.observableArray([]);
        lstAppCompltSync: KnockoutObservableArray<vmbase.AppAbsRecSyncData> = ko.observableArray([]);

        // approvalMode: KnockoutObservable<boolean> = ko.observable(false);
        approvalCount: KnockoutObservable<vmbase.ApplicationStatus> = ko.observable(new vmbase.ApplicationStatus(0, 0, 0, 0, 0, 0));
        itemList: KnockoutObservableArray<any>;
        selectedIds: KnockoutObservableArray<any> = ko.observableArray([1,5]);// check box - ver50
        dateValue: KnockoutObservable<vmbase.Date> = ko.observable({ startDate: '', endDate: '' });
        itemApplication: KnockoutObservableArray<vmbase.ChoseApplicationList> = ko.observableArray([]);
        // selectedCode: KnockoutObservable<number> = ko.observable(-1);// combo box
        mode: KnockoutObservable<number> = ko.observable(1);
        startDateString: KnockoutObservable<string> = ko.observable("");
        endDateString: KnockoutObservable<string> = ko.observable("");

        //spr
        isSpr: KnockoutObservable<boolean> = ko.observable(false);
        // extractCondition: KnockoutObservable<number> = ko.observable(0);
        //ver33
        isHidden: KnockoutObservable<boolean> = ko.observable(false);
        //_______CCG001____
        ccgcomponent: any;
        showinfoSelectedEmployee: KnockoutObservable<boolean>;
        baseDate: KnockoutObservable<Date>;
        selectedEmployee: KnockoutObservableArray<any>;
        workplaceId: KnockoutObservable<string> = ko.observable("");
        employeeId: KnockoutObservable<string> = ko.observable("");
        //ver35
        lstSidFilter: KnockoutObservableArray<string> = ko.observableArray([]);
        lstContentApp: KnockoutObservableArray<any> = ko.observableArray([]);

        //ver60
        //Grid list item
        // apptypeGridColumns: KnockoutObservable<NtsGridListColumn>;
        selectedAppId: KnockoutObservableArray<string> = ko.observableArray([]);
		orderCD: KnockoutObservable<number> = ko.observable(0);
        appListExtractConditionDto: vmbase.AppListExtractConditionDto = new vmbase.AppListExtractConditionDto(null,null,true,true,0,0,false,[],true,false,false,false,false,true,[],[]);
        columnWidth: vmbase.columnWidth = new vmbase.columnWidth(true, 340, "", "");
        appListInfo: any = null;
        appListAtr: number;
        isBeforeCheck: KnockoutObservable<boolean> = ko.observable(true);
        isAfterCheck: KnockoutObservable<boolean> = ko.observable(true);
        isLimit500: KnockoutObservable<boolean> = ko.observable(false);
        isApprove: KnockoutObservable<boolean>;
        isActiveApprove: any;

        constructor() {
            let self = this;
            $(".popup-panel").ntsPopup({
                position: {
                    my: "left top",
                    at: "left bottom",
                    of: ".hyperlink"
                },
                showOnStart: false,
                dismissible: false
            });

            $("a.hyperlink").click(() => {$(".popup-panel").ntsPopup("toggle");});
            $(window).on("mousedown.popup", function(e) {
                let control = $(".popup-panel");
                if (!$(e.target).is(control)
                    && control.has(e.target).length === 0
                    && !$(e.target).is($(".hyperlink"))) {
                    $(".popup-panel").ntsPopup("hide");
                }
            });

            self.itemList = ko.observableArray([
                { id: 1, name: getText('CMM045_20') },
                { id: 2, name: getText('CMM045_21') },
                { id: 3, name: getText('CMM045_22') },
                { id: 4, name: getText('CMM045_23') },
                { id: 5, name: getText('CMM045_24') }
            ]);

            /*self.selectedCode.subscribe(function(codeChanged) {
                self.filterByAppType(codeChanged);
            });*/

            //_____CCG001________
            self.selectedEmployee = ko.observableArray([]);
            self.showinfoSelectedEmployee = ko.observable(false);
            self.ccgcomponent = {

            showEmployeeSelection: false, // 検索タイプ
            systemType: 2, // システム区分 - 就業
            showQuickSearchTab: true, // クイック検索
            showAdvancedSearchTab: true, // 詳細検索
            showBaseDate: false, // 基準日利用
            showClosure: false, // 就業締め日利用
            showAllClosure: false, // 全締め表示
            showPeriod: false, // 対象期間利用
            periodFormatYM: true, // 対象期間精度

            /** Required parameter */
            baseDate: moment.utc().toISOString(), // 基準日
            inService: true, // 在職区分
            leaveOfAbsence: true, // 休職区分
            closed: true, // 休業区
            retirement: true, // 退職区分

            /** Quick search tab options */
            showAllReferableEmployee: true, // 参照可能な社員すべて
            showOnlyMe: true, // 自分だけ
            showSameWorkplace: true, // 同じ職場の社員
            showSameWorkplaceAndChild: true, // 同じ職場とその配下の社員

            /** Advanced search properties */
            showEmployment: true, // 雇用条件
            showWorkplace: true, // 職場条件
            showClassification: true, // 分類条件
            showJobTitle: true, // 職位条件
            showWorktype: true, // 勤種条件
            isMutipleCheck: true,
            /**
            * @param dataList: list employee returned from component.
            * Define how to use this list employee by yourself in the function's body.
            */

            returnDataFromCcg001: function(data: any){
                self.showinfoSelectedEmployee(true);
                self.selectedEmployee(data.listEmployee);
                console.log(data.listEmployee);
                self.lstSidFilter([]);
                _.each(data.listEmployee, function(emp){
                    self.lstSidFilter.push(emp.employeeId);
                });


				if(!self.checkConditionParam()) {
					return;
				}
				let empIDLst = [];
				_.each(data.listEmployee, function(emp){
                    empIDLst.push(emp.employeeId);
                });
				self.appListExtractConditionDto.opListEmployeeID = empIDLst;

				block.invisible();
				service.findByEmpIDLst(self.appListExtractConditionDto).done((data: any) => {
					return self.reload(data.appListExtractCondition, data.appListInfo);
					/*self.approvalLstDispSet = data.displaySet;
					let newItemLst = [];
					_.each(data.appLst, item => {
						newItemLst.push(new vmbase.DataModeApp(item));
					});
					self.items(newItemLst);
					if (data.appStatusCount != null) {
                        self.approvalCount(new vmbase.ApplicationStatus(data.appStatusCount.unApprovalNumber, data.appStatusCount.approvalNumber,
                            data.appStatusCount.approvalAgentNumber, data.appStatusCount.cancelNumber, data.appStatusCount.remandNumner,
                            data.appStatusCount.denialNumber));
                    }

                    if (self.mode() == 1) {
                        $("#grid1").ntsGrid("destroy");
                        let colorBackGr = self.fillColorbackGrAppr();
                        let lstHidden: Array<any> = self.findRowHidden(self.items());
                        self.reloadGridApproval(lstHidden,colorBackGr, false);
                        // self.reloadGridApproval(lstHidden,colorBackGr, self.isHidden());
                    } else {
                        let colorBackGr = self.fillColorbackGr();
                        $("#grid2").ntsGrid("destroy");
                        self.reloadGridApplicaion(colorBackGr, false);
                        // self.reloadGridApplicaion(colorBackGr, self.isHidden());
                    }*/
				}).always(() => block.clear());
                // self.filter();
             }
            }

            window.onresize = function(event) {
                if($('#grid1').length){//approval
                    $("#grid1").igGrid("option", "height", window.innerHeight - 350  + "px");
                }
                if($('#grid2').length){//application
                    $("#grid2").igGrid("option", "height", window.innerHeight - 270  + "px");
                }
            }

			self.selectedIds.subscribe(value => {
				self.appListExtractConditionDto.opUnapprovalStatus = _.includes(value, 1) ? true : false;
				self.appListExtractConditionDto.opApprovalStatus = _.includes(value, 2) ? true : false;
				self.appListExtractConditionDto.opDenialStatus = _.includes(value, 3) ? true : false;
				self.appListExtractConditionDto.opAgentApprovalStatus = _.includes(value, 4) ? true : false;
				self.appListExtractConditionDto.opRemandStatus = _.includes(value, 5) ? true : false;
				self.appListExtractConditionDto.opCancelStatus = _.includes(value, 6) ? true : false;
			});

			self.selectedAppId.subscribe(value => {
				_.each(self.appListExtractConditionDto.opListOfAppTypes, x => {
					let appType = x.appType.toString();
					if(!_.isNull(x.opApplicationTypeDisplay)) {
						appType += x.opApplicationTypeDisplay.toString();
					};
					if(_.includes(value, appType)) {
						x.choice = true;
					} else {
						x.choice = false;
					}
				});
			});

			self.isBeforeCheck.subscribe(value => {
				self.appListExtractConditionDto.preOutput = value;
			});
        	self.isAfterCheck.subscribe(value => {
				self.appListExtractConditionDto.postOutput = value;
			});

			self.orderCD.subscribe(value => {
				self.appListExtractConditionDto.appDisplayOrder = value;
			});
        }

        saveContentWidth() {
            let self = this;
            var contentWidth = $(".appContent").outerWidth();

            if(self.mode() == 0) {
                self.columnWidth.appLstAtr = true;
                self.columnWidth.width = contentWidth;
                self.columnWidth.cID = __viewContext.user.companyId;
                self.columnWidth.sID = __viewContext.user.employeeId;
            } else {
                self.columnWidth.appLstAtr = false;
                self.columnWidth.width = contentWidth;
                self.columnWidth.cID = __viewContext.user.companyId;
                self.columnWidth.sID = __viewContext.user.employeeId;
            }
            console.log(contentWidth);

            if (self.mode() == 0) {
                character.restore('TableColumnWidth0' + __viewContext.user.companyId + __viewContext.user.employeeId).then((obj) => {
                    if(obj !== undefined) {
                        if(contentWidth !== obj.width) {
                            character.save('TableColumnWidth0' + __viewContext.user.companyId + __viewContext.user.employeeId, self.columnWidth).then(() => {
                                nts.uk.ui.dialog.info({ messageId: "Msg_357" });
                            });
                        }
                    } else {
                        if(contentWidth !== 340) {
                            character.save('TableColumnWidth0' + __viewContext.user.companyId + __viewContext.user.employeeId, self.columnWidth).then(() => {
                                nts.uk.ui.dialog.info({ messageId: "Msg_357" });
                            });
                        }
                    }
                });
            } else {
                character.restore('TableColumnWidth1' + __viewContext.user.companyId + __viewContext.user.employeeId).then((obj) => {
                    if(obj !== undefined) {
                        if(contentWidth !== obj.width) {
                            character.save('TableColumnWidth1' + __viewContext.user.companyId + __viewContext.user.employeeId, self.columnWidth).then(() => {
                                nts.uk.ui.dialog.info({ messageId: "Msg_357" });
                            });
                        }
                    } else {
                        if(contentWidth !== 340) {
                            character.save('TableColumnWidth1' + __viewContext.user.companyId + __viewContext.user.employeeId, self.columnWidth).then(() => {
                                nts.uk.ui.dialog.info({ messageId: "Msg_357" });
                            });
                        }
                    }
                });
            }
        }

		checkConditionParam() {
			const self = this;
            if (nts.uk.ui.errors.hasError()) {
                return false;
            }
            //check filter
            //check startDate
            if (self.dateValue().startDate == null || self.dateValue().startDate == '') {//期間開始日付または期間終了日付が入力されていない
                $('#daterangepicker>.ntsDateRange_Container>.ntsDateRange>.ntsStartDate input').ntsError('set', {messageId:"Msg_359"});
                return false;
            }
            //check endDate
            if (self.dateValue().endDate == null || self.dateValue().endDate == '') {//期間開始日付または期間終了日付が入力されていない
                $('#daterangepicker>.ntsDateRange_Container>.ntsDateRange>.ntsEndDate input').ntsError('set', {messageId:"Msg_359"});
                return false;
            }
            if (self.mode() == 1 && self.selectedIds().length == 0) {//承認状況のチェックの確認
                nts.uk.ui.dialog.alertError({ messageId: "Msg_360" });
                return false;
            }
			if (!self.appListExtractConditionDto.preOutput && !self.appListExtractConditionDto.postOutput) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_1722" }).then(() => {
                    $(".popup-panel").ntsPopup("toggle");
                });
                return false;
			}
			let selectAppTypeLst = _.filter(self.appListExtractConditionDto.opListOfAppTypes, o => o.choice);
			if (_.isEmpty(selectAppTypeLst)) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_1723" }).then(() => {
                    $(".popup-panel").ntsPopup("toggle");
                });
                return false;
			}
			return true;
		}

		findByPeriod() {
			const self = this;
			if(!self.checkConditionParam()) {
				return;
			}
			self.appListExtractConditionDto.periodStartDate = self.dateValue().startDate;
			self.appListExtractConditionDto.periodEndDate = self.dateValue().endDate;

			block.invisible();
			service.findByPeriod(self.appListExtractConditionDto).done((data: any) => {
				return self.reload(data.appListExtractCondition, data.appListInfo);
				/*self.appListExtractConditionDto = data.appListExtractCondition;
				self.updateFromAppListExtractCondition();
				self.approvalLstDispSet = data.appListInfo.displaySet;
				let newItemLst = [];
				_.each(data.appListInfo.appLst, item => {
					newItemLst.push(new vmbase.DataModeApp(item));
				});
				self.items(newItemLst);
				if (data.appStatusCount != null) {
                    self.approvalCount(new vmbase.ApplicationStatus(data.appStatusCount.unApprovalNumber, data.appStatusCount.approvalNumber,
                        data.appStatusCount.approvalAgentNumber, data.appStatusCount.cancelNumber, data.appStatusCount.remandNumner,
                        data.appStatusCount.denialNumber));
                }

                if (self.mode() == 1) {
                    $("#grid1").ntsGrid("destroy");
                    let colorBackGr = self.fillColorbackGrAppr();
                    let lstHidden: Array<any> = self.findRowHidden(self.items());
                    self.reloadGridApproval(lstHidden,colorBackGr, false);
                    // self.reloadGridApproval(lstHidden,colorBackGr, self.isHidden());
                } else {
                    let colorBackGr = self.fillColorbackGr();
                    $("#grid2").ntsGrid("destroy");
                    self.reloadGridApplicaion(colorBackGr, false);
                    // self.reloadGridApplicaion(colorBackGr, self.isHidden());
              	}*/
			}).always(() => block.clear());
		}

        start(): JQueryPromise<any> {
			const self = this;
            block.invisible();
//            let self = this;
//            var dfd = $.Deferred();
            //get param url
            /*let url = $(location).attr('search');
            let urlParam: number = undefined;
			if(_.isUndefined(url.split("=")[1])) {
				history.pushState({}, null, "?a=0");
			} else {
				urlParam = parseInt(url.split("=")[1]);
			}*/
            // let characterData = null;
            // let appCHeck = null;
            /*if (urlParam !== undefined) {
                character.save('AppListExtractCondition', null);
            }*/
            //get param spr
            let paramSprCmm045: vmbase.IntefaceSPR = __viewContext.transferred.value == null ?
                    null : __viewContext.transferred.value.PARAM_SPR_CMM045;
            //spr call
            if(paramSprCmm045 !== undefined && paramSprCmm045 !== null){
                // character.save('AppListExtractCondition', null);
                let date: vmbase.Date = { startDate: paramSprCmm045.startDate, endDate: paramSprCmm045.endDate }
                self.dateValue(date);
                self.mode(paramSprCmm045.mode);
                self.isSpr(true);
                // self.extractCondition(paramSprCmm045.extractCondition);
            }
            return character.restore("AppListExtractCondition").then((obj) => {
				// characterData = obj;
                if (obj !== undefined && obj !== null && !self.isSpr()) {
					self.appListExtractConditionDto = obj;
					self.updateFromAppListExtractCondition();
                    /*let date: vmbase.Date = { startDate: obj.periodStartDate, endDate: obj.periodEndDate }
                    self.dateValue(date);
                    self.selectedIds([]);
                    if (obj.opUnapprovalStatus) {//未承認
                        self.selectedIds.push(1);
                    }
                    if (obj.opApprovalStatus) {//承認済み
                        self.selectedIds.push(2);
                    }
                    if (obj.opDenialStatus) {//否認
                        self.selectedIds.push(3);
                    }
                    if (obj.opAgentApprovalStatus) {//代行承認済み
                        self.selectedIds.push(4);
                    }
                    if (obj.opRemandStatus) {//差戻
                        self.selectedIds.push(5);
                    }
                    if (obj.opCancelStatus) {//取消
                        self.selectedIds.push(6);
                    }*/
//                    self.selectedRuleCode(obj.appDisplayAtr);
                    //combo box
                    // appCHeck = obj.appType;
                    // self.lstSidFilter(obj.opListEmployeeID);
					// self.selectedAppId(_.chain(obj.opListOfAppTypes).filter(o => o.choice).map((x: any) => x.appType).value());
					character.remove('AppListExtractCondition');
                }
				let url = $(location).attr('search');
	            let urlParam: number = undefined;
				if(!_.isUndefined(url.split("=")[1])) {
					urlParam = parseInt(url.split("=")[1]);
				}
                if (urlParam === undefined && !self.isSpr()) {
					if (obj !== undefined && obj !== null) {
						self.mode(obj.appListAtr);
					} else {
						self.mode(1);
					}
					history.pushState({}, null, "?a="+self.mode());
                }
                if(urlParam !== undefined && !self.isSpr()){
                    self.mode(urlParam);
                }
                //write log
                let paramLog = {programId: 'CMM045',
                                screenId: 'A',
                                queryString: 'a='+self.mode()};
                service.writeLog(paramLog);
//                let condition: vmbase.AppListExtractConditionDto = new vmbase.AppListExtractConditionDto(self.dateValue().startDate, self.dateValue().endDate, self.mode(),
//                    self.selectedCode(), self.findcheck(self.selectedIds(), 1), self.findcheck(self.selectedIds(), 2), self.findcheck(self.selectedIds(), 3),
//                    self.findcheck(self.selectedIds(), 4), self.findcheck(self.selectedIds(), 5), self.findcheck(self.selectedIds(), 6), 0, self.lstSidFilter(), '');
//                let param = {   condition: condition,
//                                spr: self.isSpr(),
//                                extractCondition: self.extractCondition(),
//                                device: 0,
//                                lstAppType: []
//                            };
				return service.getAppNameInAppList();
			}).then((data: any) => {
				if(_.isEmpty(self.appListExtractConditionDto.opListOfAppTypes)) {
					self.appListExtractConditionDto.opListOfAppTypes = data;
				}
				self.updateFromAppListExtractCondition();
				// self.selectedAppId(_.chain(self.appListExtractConditionDto.opListOfAppTypes).filter(o => o.choice).map(x => x.appType).value());
                _.forEach(data, item => {
                    if(item.appName != "") {
						let appType = item.appType.toString();
						if(!_.isNull(item.opApplicationTypeDisplay)) {
							appType += item.opApplicationTypeDisplay.toString();
						};
                        self.itemApplication().push(new vmbase.ChoseApplicationList(appType, item.appName));
                    }
                });
                _.uniqBy(self.itemApplication(), ['appType', 'appName']);
				let newParam: any = {
					mode: self.mode(),
					device: 0,
					listOfAppTypes: data,
					appListExtractCondition: self.appListExtractConditionDto
                };
				if(paramSprCmm045 !== undefined && paramSprCmm045 !== null) {
					newParam.startDate = paramSprCmm045.startDate;
					newParam.endDate = paramSprCmm045.endDate;
				}

                // self.itemList()
				return service.getApplicationList(newParam);
			}).then((data: any) => {
				return self.reload(data.appListExtractCondition, data.appListInfo);
//                if(self.appList().appLst.length > 500) {
//
//                }
//                self.isLimit500(data.appListInfo.moreThanDispLineNO);
//                // self.appListAtr = data.appListExtractCondition.appListAtr;
//				// self.dateValue({ startDate: data.appListInfo.displaySet.startDateDisp, endDate: data.appListInfo.displaySet.endDateDisp });
//				self.appListExtractConditionDto = data.appListExtractCondition;
//				self.updateFromAppListExtractCondition();
//				self.approvalLstDispSet = data.appListInfo.displaySet;
//                self.lstContentApp(data.lstContentApp);
//                let isHidden = data.isDisPreP == 1 ? true : true;
//                self.isHidden(isHidden);
//                        self.selectedRuleCode.subscribe(function(codeChanged) {
//                            self.filter();
//                        });
                        //luu param
                        /*if (self.dateValue().startDate == '' || self.dateValue().endDate == '') {
                            let date: vmbase.Date = { startDate: data.startDate, endDate: data.endDate }
                            self.dateValue(date);
                        }*/
//                let paramSave: vmbase.AppListExtractConditionDto = new vmbase.AppListExtractConditionDto(self.dateValue().startDate, self.dateValue().endDate, self.mode(),
//                    self.selectedCode(), self.findcheck(self.selectedIds(), 1), self.findcheck(self.selectedIds(), 2), self.findcheck(self.selectedIds(), 3),
//                    self.findcheck(self.selectedIds(), 4), self.findcheck(self.selectedIds(), 5), self.findcheck(self.selectedIds(), 6), 0, self.lstSidFilter(), '');
                // character.save('AppListExtractCondition', self.appListExtractConditionDto);
//                _.each(data.lstApp, function(app) {
//                    self.lstAppCommon.push(new vmbase.ApplicationDataOutput(app.applicationID, app.prePostAtr, app.inputDate,
//                        app.enteredPersonSID, app.applicationDate, app.applicationType, app.applicantSID, app.reflectPerState,
//                        app.startDate, app.endDate, app.version, app.reflectStatus));
//                });
//                _.each(data.lstMasterInfo, function(master) {
//                    self.lstAppMaster.push(new vmbase.AppMasterInfo(master.appID, master.appType, master.dispName, master.empName,master.inpEmpName,
//                        master.workplaceName, master.statusFrameAtr, master.phaseStatus, master.checkAddNote, master.checkTimecolor, master.detailSet));
//                });
//                self.itemApplication([]);
//                _.each(data.lstAppInfor, function(appInfo){
//                    self.itemApplication.push(new vmbase.ChoseApplicationList(appInfo.appType, appInfo.appName));
//                });
//                self.lstListAgent([]);
//                _.each(data.lstAgent, function(agent){
//                    self.lstListAgent.push(new vmbase.ApproveAgent(agent.appID, agent.agentId));
//                });
//                _.each(data.lstSyncData, function(complt){
//                    self.lstAppCompltSync.push(new vmbase.AppAbsRecSyncData(complt.typeApp, complt.appMainID, complt.appSubID, complt.appDateSub));
//                });
//                let lstData = self.mapData(self.lstAppCommon(), self.lstAppMaster(), self.lstAppCompltSync());
//                self.lstApp(lstData);
//				let newItemLst = [];
//				_.each(data.appListInfo.appLst, item => {
//					newItemLst.push(new vmbase.DataModeApp(item));
//				});
//				self.items(newItemLst);
//                //mode approval - count
//                if (data.appStatusCount != null) {
//                    self.approvalCount(new vmbase.ApplicationStatus(data.appStatusCount.unApprovalNumber, data.appStatusCount.approvalNumber,
//                        data.appStatusCount.approvalAgentNumber, data.appStatusCount.cancelNumber, data.appStatusCount.remandNumner,
//                        data.appStatusCount.denialNumber));
//                }
//                if (self.mode() == 1) {
//                    let colorBackGr = self.fillColorbackGrAppr();
//                     let lstHidden: Array<any> = self.findRowHidden(self.items());
//                    //  self.reloadGridApproval(lstHidden,colorBackGr, self.isHidden());
//                     self.reloadGridApproval(lstHidden,colorBackGr, false);
//                } else {
//                    let colorBackGr = self.fillColorbackGr();
//                    self.reloadGridApplicaion(colorBackGr, self.isHidden());
//                }
                /*if(appCHeck != null){
                    self.selectedCode(appCHeck);
                }
                if(self.isSpr()){
                    let selectedType = paramSprCmm045.extractCondition == 0 ? -1 : 0;
                    self.selectedCode(selectedType);
                }*/
                // if(self.mode() == 0){

                // }
                // dfd.resolve();
			}).then((data) => {
				if(data) {
					$('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
				}
			}).always(() => block.clear());

//                service.getApplicationDisplayAtr().done(function(data1) {
//                    _.each(data1, function(obj) {
//                        self.roundingRules.push(new vmbase.ApplicationDisplayAtr(obj.value, obj.localizedName));
//                    });
//					service.getAppNameInAppList().then((data) => {
//						let newParam = {
//							mode: 0,
//							device: 0,
//							listOfAppTypes: data
//						};
//
//
//                    service.getApplicationList(newParam).done(function(data: any) {
//						self.dateValue({ startDate: data.appListInfoDto.displaySet.startDateDisp, endDate: data.appListInfoDto.displaySet.endDateDisp });
//                        self.lstContentApp(data.lstContentApp);
//                        let isHidden = data.isDisPreP == 1 ? false : true;
//                        self.isHidden(isHidden);
////                        self.selectedRuleCode.subscribe(function(codeChanged) {
////                            self.filter();
////                        });
//                        //luu param
//                        /*if (self.dateValue().startDate == '' || self.dateValue().endDate == '') {
//                            let date: vmbase.Date = { startDate: data.startDate, endDate: data.endDate }
//                            self.dateValue(date);
//                        }*/
//                        let paramSave: vmbase.AppListExtractConditionDto = new vmbase.AppListExtractConditionDto(self.dateValue().startDate, self.dateValue().endDate, self.mode(),
//                            self.selectedCode(), self.findcheck(self.selectedIds(), 1), self.findcheck(self.selectedIds(), 2), self.findcheck(self.selectedIds(), 3),
//                            self.findcheck(self.selectedIds(), 4), self.findcheck(self.selectedIds(), 5), self.findcheck(self.selectedIds(), 6), 0, self.lstSidFilter(), '');
//                        character.save('AppListExtractCondition', paramSave);
//                        _.each(data.lstApp, function(app) {
//                            self.lstAppCommon.push(new vmbase.ApplicationDataOutput(app.applicationID, app.prePostAtr, app.inputDate,
//                                app.enteredPersonSID, app.applicationDate, app.applicationType, app.applicantSID, app.reflectPerState,
//                                app.startDate, app.endDate, app.version, app.reflectStatus));
//                        });
//                        _.each(data.lstMasterInfo, function(master) {
//                            self.lstAppMaster.push(new vmbase.AppMasterInfo(master.appID, master.appType, master.dispName, master.empName,master.inpEmpName,
//                                master.workplaceName, master.statusFrameAtr, master.phaseStatus, master.checkAddNote, master.checkTimecolor, master.detailSet));
//                        });
//                        self.itemApplication([]);
//                        _.each(data.lstAppInfor, function(appInfo){
//                            self.itemApplication.push(new vmbase.ChoseApplicationList(appInfo.appType, appInfo.appName));
//                        });
//                        self.lstListAgent([]);
//                        _.each(data.lstAgent, function(agent){
//                            self.lstListAgent.push(new vmbase.ApproveAgent(agent.appID, agent.agentId));
//                        });
//                        _.each(data.lstSyncData, function(complt){
//                            self.lstAppCompltSync.push(new vmbase.AppAbsRecSyncData(complt.typeApp, complt.appMainID, complt.appSubID, complt.appDateSub));
//                        });
//                        let lstData = self.mapData(self.lstAppCommon(), self.lstAppMaster(), self.lstAppCompltSync());
//                        self.lstApp(lstData);
//                        self.items(data.appListInfoDto.appLst);
//                        //mode approval - count
//                        if (data.appStatusCount != null) {
//                            self.approvalCount(new vmbase.ApplicationStatus(data.appStatusCount.unApprovalNumber, data.appStatusCount.approvalNumber,
//                                data.appStatusCount.approvalAgentNumber, data.appStatusCount.cancelNumber, data.appStatusCount.remandNumner,
//                                data.appStatusCount.denialNumber));
//                        }
//                        if (self.mode() == 1) {
//                            let colorBackGr = self.fillColorbackGrAppr();
//                             let lstHidden: Array<any> = self.findRowHidden(self.items());
//                             self.reloadGridApproval(lstHidden,colorBackGr, self.isHidden());
//                        } else {
//                            let colorBackGr = self.fillColorbackGr();
//                            self.reloadGridApplicaion(colorBackGr, self.isHidden());
//                        }
//                        if(appCHeck != null){
//                            self.selectedCode(appCHeck);
//                        }
//                        if(self.isSpr()){
//                            let selectedType = paramSprCmm045.extractCondition == 0 ? -1 : 0;
//                            self.selectedCode(selectedType);
//                        }
//                        if(self.mode() == 0){
//                            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
//                        }
//                        block.clear();
//                        dfd.resolve();
//                    });
//					});
//                }).fail(()=>{
//                    block.clear();
//                });
//            });
            //return dfd.promise();
        }

		reload(appListExtractCondition: any, appListInfo: any) {
			const self = this;
			if(!_.isNull(appListExtractCondition)) {
				self.appListExtractConditionDto = appListExtractCondition;
				self.updateFromAppListExtractCondition();
			}
			self.appListInfo = appListInfo;
			let newItemLst = [];
			_.each(appListInfo.appLst, item => {
				newItemLst.push(new vmbase.DataModeApp(item));
			});
			self.items(newItemLst);
			//if (appListInfo.numberOfApp != null) {
            self.approvalCount(new vmbase.ApplicationStatus(
				appListInfo.numberOfApp.unApprovalNumber,
				appListInfo.numberOfApp.approvalNumber,
                appListInfo.numberOfApp.approvalAgentNumber,
				appListInfo.numberOfApp.cancelNumber,
				appListInfo.numberOfApp.remandNumner,
                appListInfo.numberOfApp.denialNumber));
            //}

            if (self.mode() == 1) {
                $("#grid1").ntsGrid("destroy");
                let colorBackGr = self.fillColorbackGrAppr();
                let lstHidden: Array<any> = self.findRowHidden(self.items());
                self.reloadGridApproval(lstHidden,colorBackGr, false);
                // self.reloadGridApproval(lstHidden,colorBackGr, self.isHidden());
            } else {
                let colorBackGr = self.fillColorbackGr();
                $("#grid2").ntsGrid("destroy");
                self.reloadGridApplicaion(colorBackGr, false);
                // self.reloadGridApplicaion(colorBackGr, self.isHidden());
          	}
            self.isLimit500(appListInfo.moreThanDispLineNO);
            self.isApprove = ko.computed(() => {
                return self.mode() == 1 && self.items().length > 0 && _.filter(self.items(), x => x.checkAtr).length > 0 && self.appListInfo.displaySet.appDateWarningDisp !== 0;
            }, self);
            self.isActiveApprove = ko.computed(() => {
                return self.items().length > 0 && _.filter(self.items(), x => x.checkAtr).length > 0;
            }, self);

			/*self.appList(data.appListInfo);
            if(self.appList().appLst.length > 500) {

            }
            self.isLimit500(data.appListInfo.moreThanDispLineNO);
            self.lstContentApp(data.lstContentApp);
            let isHidden = data.isDisPreP == 1 ? true : true;
            self.isHidden(isHidden);

            // if(self.mode() == 0){
                $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
            // }*/
			return true;
		}

		updateFromAppListExtractCondition() {
			const self = this;
			let obj = self.appListExtractConditionDto,
				date: vmbase.Date = { startDate: obj.periodStartDate, endDate: obj.periodEndDate }
            self.dateValue(date);
            let arraySelectedIds = [];
            if (obj.opUnapprovalStatus) {//未承認
                arraySelectedIds.push(1);
            }
            if (obj.opApprovalStatus) {//承認済み
                arraySelectedIds.push(2);
            }
            if (obj.opDenialStatus) {//否認
                arraySelectedIds.push(3);
            }
            if (obj.opAgentApprovalStatus) {//代行承認済み
                arraySelectedIds.push(4);
            }
            if (obj.opRemandStatus) {//差戻
                arraySelectedIds.push(5);
            }
            if (obj.opCancelStatus) {//取消
                arraySelectedIds.push(6);
            }
			self.orderCD(obj.appDisplayOrder);
			self.selectedIds(arraySelectedIds);
			self.lstSidFilter(obj.opListEmployeeID);
			self.selectedAppId(_.chain(obj.opListOfAppTypes).filter(o => o.choice).map(x => {
				let appType = x.appType.toString();
				if(!_.isNull(x.opApplicationTypeDisplay)) {
					appType += x.opApplicationTypeDisplay.toString();
				};
				return appType;
			}).value());
			self.appListAtr = obj.appListAtr;
		}

        setupGrid(options: {
            withCcg001: boolean,
            width: any,
            height: any,
            columns: Array<{
                headerText: string,
                width: string,
                key: string,
                extraClassProperty?: string,
                checkbox?: { visible: Function, applyToProperty: string },
                button?: { text: string, click: Function }
            }>
        }) {

            let $container = $("#app-grid-container");
            $container.hide();

            if ($container.children("#not-constructed").length === 1) {

                $container.empty();

                if (options.withCcg001) {
                    $container.addClass("with-ccg001");
                    $("#app-resize").addClass("with-ccg001");
                }

                // header
                let $colgroup = $("<colgroup/>");
                let $trHead = $("<tr/>");
                options.columns.forEach(column => {
                    $("<col/>")
                        .attr("width", column.width)
                        .appendTo($colgroup)
                        .addClass(column.key === 'appContent' ? 'appContent' : '');

                    let $th = $("<th/>")
                        .addClass("ui-widget-header");

                    // batch check
                    if (column.checkbox !== undefined) {
                        let items = this.items();
                        let checkableItems = items.filter(item => item.checkAtr === true);
                        if (checkableItems.length > 0) {
                            $("<label/>")
                                .addClass("ntsCheckBox")
                                .append($("<input/>")
                                    .attr("id", "batch-check")
                                    .attr("type", "checkbox")
                                    .addClass(column.key))
                                .append($("<span/>").addClass("box"))
                                .change((e) => {
                                    let checked = $(e.target).prop("checked");
                                    $appGrid.find("input[type=checkbox]." + column.key)
                                        .prop("checked", checked);
                                    items
                                        .filter(item => item.checkAtr === true)
                                        .forEach(item => item.check = checked);
                                })
                                .appendTo($th);
                        }
                    }
                    else {
                        $th.text(column.headerText);
                    }

                    $th.appendTo($trHead);
                });
                let $thead = $("<thead/>")
                    .append($trHead);

                // body
                let $tbody = $("<tbody/>");


                // build grid
                let $appGrid = $("<table/>")
                    .attr("id", "app-grid")
                    .data("size", { width: options.width, height: options.height })
                    .append($colgroup)
                    .append($thead)
                    .append($tbody);

                // event handler
                options.columns.forEach(column => {
                    if (column.button !== undefined) {
                        $appGrid.on("click", "." + column.key, column.button.click);
                    }

                    if (column.checkbox !== undefined) {
                        $appGrid.on("change", "input." + column.key, e => {
                            let appId = $(e.target).closest("td").data("app-id");
                            let checked = $(e.target).prop("checked");
                            let items = this.items();
                            items.filter(item => item.appID === appId)[0].check = checked;

                            // sync with batch check
                            let allChecked = true;
                            for (let i = 0; i < items.length; i++) {
                                let item = items[i];
                                if (item.checkAtr === false) continue;
                                if (item.check === false) {
                                    allChecked = false;
                                    break;
                                }
                            }
                            $("#batch-check").prop("checked", allChecked);
                        });
                    }
                });

                $container.append($appGrid).show();

                let size = $appGrid.data("size");
                fixedTable.init($appGrid, {
                    height: size.height,
                    width: size.width
                });

                dragResize(
                    $container.find(".nts-fixed-header-container table"),
                    $container.find(".nts-fixed-body-container table"));
            }

            // $("#app-resize").css("width", options.width - 20);

            this.loadGridData(options.columns);

            $container.show();
        }

        loadGridData(
            columns: Array<{
                headerText: string,
                width: string,
                key: string,
                extraClassProperty?: string,
                checkbox?: { visible: Function, applyToProperty: string },
                button?: { text: string, click: Function }
            }>) {
			const self = this;
            let $container = $("#app-grid-container");
            let $tbody = $container.find(".nts-fixed-body-wrapper tbody");
            $tbody.empty();

            self.items().forEach((item, i) => {
                let $tr = $("<tr/>");
                columns.forEach(column => {

                    let $td = $("<td/>")
                        .data("app-id", item.appID)
                        .addClass(column.key);

                    if (column.extraClassProperty !== undefined) {
                        $td.addClass(item[column.extraClassProperty]);
                    }

                    if (column.checkbox !== undefined) {
                        var extraClass = "";
                        if(self.appListInfo.displaySet.appDateWarningDisp !== 0 && moment(item.opAppStartDate).add(-(self.appListInfo.displaySet.appDateWarningDisp), "days") <= moment.utc()) {
                            extraClass = "approvalCell";
                        } else {
                            extraClass = "";
                        }
                        if (column.checkbox.visible(item) === true) {
                            $("<label/>")
                                .addClass("ntsCheckBox")
                                .append($("<input/>")
                                    .attr("type", "checkbox")
                                    .addClass(column.key))
                                .append($("<span/>").addClass("box"))
                                .appendTo($td)
                                .parent("td")
                                .addClass(extraClass);
                        }
                    }
                    else if (column.button !== undefined) {
                        $("<button/>")
                            .addClass(column.key)
                            .text(column.button.text)
                            .appendTo($td);
                    }
                    else if(column.key == 'appDate') {
                        var date = moment(item.opAppStartDate).format("M/D(ddd)");
                        if(item.opAppStartDate !== item.opAppEndDate) {
                            date = self.appDateRangeColor(moment(item.opAppStartDate).format("M/D(ddd)"), moment(item.opAppEndDate).format("M/D(ddd)"));
                            $td.html(date);
                        } else {
                            $td.html(self.appDateColor(date, "", ""));
                        }
                        if(item.appType === 10) {

                        }
                    }
                    else {
                        $td.html(self.customContent(column.key, item));
                    }

                    $("td.appType").css("white-space", "normal");

                    $td.appendTo($tr);
                });
                $tr.appendTo($tbody);
            });

            resetColumnsSize(
                $container.find("colgroup").eq(0).children(),
                $container.find(".nts-fixed-header-wrapper table"),
                $container.find(".nts-fixed-body-wrapper table"));
        }

		customContent(key: string, item: any) {

			const self = this;
			if(key=='applicantName') {
				let nameStr = '';
				if(!_.isNull(self.appListInfo) && self.appListInfo.displaySet.workplaceNameDisp==1) {
					//if(!nts.uk.util.isNullOrEmpty(item.workplaceName)) {
					nameStr += item.workplaceName + '\n';
					//}
				}
				nameStr += item[key];
				return _.escape(nameStr).replace(/\n/g, '<br/>');
			}

			if(key=='appType') {
				let appInfo = { appName: ''};
				if(_.isNull(item.application.opStampRequestMode)) {
					appInfo = _.find(self.appListExtractConditionDto.opListOfAppTypes, o => o.appType == item[key]);
				} else {
					if(item.application.opStampRequestMode==0) {
						appInfo = _.find(self.appListExtractConditionDto.opListOfAppTypes, o => o.appType == item[key] && o.opApplicationTypeDisplay==3);
					} else {
						appInfo = _.find(self.appListExtractConditionDto.opListOfAppTypes, o => o.appType == item[key] && o.opApplicationTypeDisplay==4);
					}
				}

				if(_.isUndefined(appInfo)) {
					return '';
				} else {
					return _.escape(appInfo.appName);
				}
			}
			if(key=='prePostAtr') {
				if(item[key]==0) {
					return _.escape(nts.uk.resource.getText('KAF000_47'));
				} else {
					return _.escape(nts.uk.resource.getText('KAF000_48'));
				}
			}
			if(key=='appContent') {
				return _.escape(item[key]).replace(/\n/g, '<br/>');
            }
            if(key=='inputDate') {
                var cl = "";
                var time = moment(item[key]).format("M/D(ddd) H:mm");
                // var time = nts.uk.time.formatDate(new Date(item[key]), "m/dD hh:mm");

                if(_.includes(time, ''))
                return self.inputDateColor(time, cl);
            }
			if(key=='reflectionStatus') {
				return _.escape(getText(item[key]));
			}
			return _.escape(item[key]);
		}

        reloadGridApplicaion(colorBackGr: any, isHidden: boolean) {

            var self = this;
            let widthAuto = isHidden == false ? 1175 : 1110;
            // let widthAuto = isHidden == false ? 1250 : 1185;
            // widthAuto = screen.width - 100 >= widthAuto ? widthAuto : screen.width - 100;
            widthAuto = window.innerWidth >= 1280 ? window.innerWidth - 130 : 1100;

            var contentWidth = 340;
            character.restore('TableColumnWidth0' + __viewContext.user.companyId + __viewContext.user.employeeId).then((obj) => {
                    if(obj !== undefined && self.mode() === 0 && obj.appLstAtr === true && obj.cID === __viewContext.user.companyId && obj.sID === __viewContext.user.employeeId) {
                        contentWidth = obj.width;
                    } else {
                        contentWidth = widthAuto - 55 - 120 - 90 - 65- 155 - 120 - 75 - 95;
                    }
            }).then(() => {
                let columns = [
                    { headerText: getText('CMM045_50'), key: 'details', width: '55px', button: {
                        text: getText('CMM045_50'),
                        click: (e) => {
                            let targetAppId = $(e.target).closest("td").data("app-id");
                            let lstAppId = self.items().map(app => app.appID);
                            // window.localStorage.setItem('UKProgramParam', 'a=0');
                            character.save('AppListExtractCondition', self.appListExtractConditionDto).then(() => {
								nts.uk.request.jump("/view/kaf/000/b/index.xhtml", { 'listAppMeta': lstAppId, 'currentApp': targetAppId });
							});
                        }
                    } },
                    { headerText: getText('CMM045_51'), key: 'applicantName', width: '120px',  },
                    { headerText: getText('CMM045_52'), key: 'appType', width: '90px'},
                    { headerText: getText('CMM045_53'), key: 'prePostAtr', width: '65px', hidden: false},
                    { headerText: getText('CMM045_54'), key: 'appDate', width: '155px'},
                    { headerText: getText('CMM045_55'), key: 'appContent', width: contentWidth},
                    // { headerText: getText('CMM045_55'), key: 'appContent', width: '340px'},
                    { headerText: getText('CMM045_56'), key: 'inputDate', width: '120px'},
                    { headerText: getText('CMM045_57'), key: 'reflectionStatus', width: '75px', extraClassProperty: "appStatusName"},
                    { headerText: getText('CMM045_58'), key: 'opApprovalStatusInquiry', width: '95px' }
                ];
                let heightAuto = window.innerHeight >= 768 ? window.innerHeight - 345 : 305;
                // let heightAuto = window.innerHeight - 342 >= 325 ? window.innerHeight - 342 : 325;
                this.setupGrid({
                    withCcg001: true,
                    width: widthAuto,
                    height: heightAuto,
                    columns: columns.filter(c => c.hidden !== true),
                });

                $("#app-resize").css("width", widthAuto);
            });


/*
            $("#grid2").ntsGrid({
                width: widthAuto,
                height: window.innerHeight -250,
                dataSource: self.items(),
                primaryKey: 'appId',
                virtualization: true,
                rows: 8,
                hidePrimaryKey: true,
                rowVirtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: 'ID', key: 'appId', dataType: 'string', width: '0px', hidden: true },
                    { headerText: getText('CMM045_50'), key: 'details', dataType: 'string', width: '55px', unbound: false, ntsControl: 'Button' },
                    { headerText: getText('CMM045_51'), key: 'applicant', dataType: 'string', width: '120px' },
                    { headerText: getText('CMM045_52'), key: 'appName', dataType: 'string', width: '90px'},
                    { headerText: getText('CMM045_53'), key: 'appAtr', dataType: 'string', width: '65px', hidden: isHidden},
                    { headerText: getText('CMM045_54'), key: 'appDate', dataType: 'string', width: '157px'},
                    { headerText: getText('CMM045_55'), key: 'appContent', dataType: 'string', width: '408px'},
                    { headerText: getText('CMM045_56'), key: 'inputDate', dataType: 'string', width: '120px'},
                    { headerText: getText('CMM045_57'), key: 'appStatus', dataType: 'string', width: '75px'}
                ],
                features: [
                    { name: 'Resizing' },
                    {
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: true
                    }
                ],
                ntsFeatures:[
                    {
                        name: 'CellState',
                        rowId: 'rowId',
                        columnKey: 'columnKey',
                        state: 'state',
                        states: colorBackGr
                    },
                ],
                ntsControls: [{ name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                    { name: 'Button', text: getText('CMM045_50'), controlType: 'Button', enable: true },
                ]
            });
            $("#grid2").on("click", ".ntsButton", function(evt, ui) {
                let _this = $(this);
                let id = _this.parents('tr').data('id');
                //Bug #97203 - EA2540
//                let a = self.findDataModeAppByID(id, self.items());
//                let lstAppId = self.findListAppType(a.appType);
                let lstAppId = [];
                _.each(self.items(), function(app){
                    lstAppId.push(app.appId);
                });
                nts.uk.localStorage.setItem('UKProgramParam', 'a=0');
                nts.uk.request.jump("/view/kaf/000/b/index.xhtml", { 'listAppMeta': lstAppId, 'currentApp': id });
            });
            */
        }
        /*findDataModeAppByID(appId: string, lstAppCommon: Array<vmbase.DataModeApp>){
            return _.find(lstAppCommon, function(app) {
                return app.appId == appId;
            });
        }
        findListAppType(appType: number){
            let self = this;
            let lstAppId = [];
            _.each(self.items(), function(item){
                if(item.appType == appType){
                    lstAppId.push(item.appId);
                }
            });
            return lstAppId;
        }*/

        fillColorbackGr(): Array<vmbase.CellState>{
            let self = this;
            let result = [];
            _.each(self.items(), function(item) {
                let rowId = item.appID;
                // //fill color in 承認状況
                // if (item.appStatusNo == 0) {//0 下書き保存/未反映　=　未
                //     item.appStatusName = 'unapprovalCell';
                //     result.push(new vmbase.CellState(rowId,'appStatus',['unapprovalCell']));
                // }
                // if (item.appStatusNo == 1) {//1 反映待ち　＝　承認済み
                //     item.appStatusName = 'approvalCell';
                //     result.push(new vmbase.CellState(rowId,'appStatus',['approvalCell']));
                // }
                // if (item.appStatusNo == 2) {//2 反映済　＝　反映済み
                //     item.appStatusName = 'reflectCell';
                //     result.push(new vmbase.CellState(rowId,'appStatus',['reflectCell']));
                // }
                // if (item.appStatusNo == 3 || item.appStatusNo == 4) {//3,4 取消待ち/取消済　＝　取消
                //     item.appStatusName = 'cancelCell';
                //     result.push(new vmbase.CellState(rowId,'appStatus',['cancelCell']));
                // }
                // if (item.appStatusNo == 5) {//5 差し戻し　＝　差戻
                //     item.appStatusName = 'remandCell';
                //     result.push(new vmbase.CellState(rowId,'appStatus',['remandCell']));
                // }
                // if (item.appStatusNo == 6) {//6 否認　=　否
                //     item.appStatusName = 'denialCell';
                //     result.push(new vmbase.CellState(rowId,'appStatus',['denialCell']));
                // }
                // //fill color in 申請内容
                // if (item.checkTimecolor == 1) {//1: xin truoc < xin sau; k co xin truoc; xin truoc bi denail
                //     result.push(new vmbase.CellState(rowId,'appContent',['preAppExcess']));
                // }
                // if (item.checkTimecolor == 2) {////2: thuc te < xin sau
                //     result.push(new vmbase.CellState(rowId,'appContent',['workingResultExcess']));
                // }

                if(item.reflectionStatus === 'CMM045_63') {
                    item.appStatusName = 'approvalCell';
                    result.push(new vmbase.CellState(rowId,'reflectionStatus',['approvalCell']));
                }
                if(item.reflectionStatus === 'CMM045_64') {
                    item.appStatusName = 'reflectCell';
                    result.push(new vmbase.CellState(rowId,'reflectionStatus',['reflectCell']));
                }
                if(item.reflectionStatus === 'CMM045_65') {
                    item.appStatusName = 'denialCell';
                    result.push(new vmbase.CellState(rowId,'reflectionStatus',['denialCell']));
                }
                if(item.reflectionStatus === 'CMM045_62') {
                    item.appStatusName = 'unapprovalCell';
                    result.push(new vmbase.CellState(rowId,'reflectionStatus',['unapprovalCell']));
                }
                if(item.reflectionStatus === 'CMM045_66') {
                    item.appStatusName = 'remandCell';
                    result.push(new vmbase.CellState(rowId,'reflectionStatus',['remandCell']));
                }
                if(item.reflectionStatus === 'CMM045_67') {
                    item.appStatusName = 'cancelCell';
                    result.push(new vmbase.CellState(rowId,'reflectionStatus',['cancelCell']));
                }
            });
            return result;
        }
        fillColorbackGrAppr(): Array<vmbase.CellState>{
            let self = this;
            let result = [];
            _.each(self.items(), function(item) {
                let rowId = item.appID;
                //fill color in 承認状況
                // if (item.appStatusNo == 5) {//5 -UNAPPROVED 未
                //     item.appStatusName = 'unapprovalCell';
                //     result.push(new vmbase.CellState(rowId,'appStatus',['unapprovalCell']));
                // }
                // if (item.appStatusNo == 4) {//4 APPROVED 承認済み
                //     item.appStatusName = 'approvalCell';
                //     result.push(new vmbase.CellState(rowId,'appStatus',['approvalCell']));
                // }
                // if (item.appStatusNo == 3) {//3 CANCELED 取消
                //     item.appStatusName = 'cancelCell';
                //     result.push(new vmbase.CellState(rowId,'appStatus',['cancelCell']));
                // }
                // if (item.appStatusNo == 2) {//2 REMAND 差戻
                //     item.appStatusName = 'remandCell';
                //     result.push(new vmbase.CellState(rowId,'appStatus',['remandCell']));
                // }
                // if (item.appStatusNo == 1) {//1 DENIAL 否
                //     item.appStatusName = 'denialCell';
                //     result.push(new vmbase.CellState(rowId,'appStatus',['denialCell']));
                // }
                // //fill color in 申請内容
                // if (item.checkTimecolor == 1) {//1: xin truoc < xin sau; k co xin truoc; xin truoc bi denail
                //     result.push(new vmbase.CellState(rowId,'appContent',['preAppExcess']));
                // }
                // if (item.checkTimecolor == 2) {////2: thuc te < xin sau
                //     result.push(new vmbase.CellState(rowId,'appContent',['workingResultExcess']));
                // }

                if(item.reflectionStatus === 'CMM045_63') {
                    item.appStatusName = 'approvalCell';
                    result.push(new vmbase.CellState(rowId,'reflectionStatus',['approvalCell']));
                }
                if(item.reflectionStatus === 'CMM045_64') {
                    item.appStatusName = 'reflectCell';
                    result.push(new vmbase.CellState(rowId,'reflectionStatus',['reflectCell']));
                }
                if(item.reflectionStatus === 'CMM045_65') {
                    item.appStatusName = 'denialCell';
                    result.push(new vmbase.CellState(rowId,'reflectionStatus',['denialCell']));
                }
                if(item.reflectionStatus === 'CMM045_62') {
                    item.appStatusName = 'unapprovalCell';
                    result.push(new vmbase.CellState(rowId,'reflectionStatus',['unapprovalCell']));
                }
                if(item.reflectionStatus === 'CMM045_66') {
                    item.appStatusName = 'remandCell';
                    result.push(new vmbase.CellState(rowId,'reflectionStatus',['remandCell']));
                }
                if(item.reflectionStatus === 'CMM045_67') {
                    item.appStatusName = 'cancelCell';
                    result.push(new vmbase.CellState(rowId,'reflectionStatus',['cancelCell']));
                }
            });
            return result;
        }
        fillColorText(): Array<vmbase.TextColor>{
            //fill color text
            let self = this;
            let result = [];
            _.each(self.items(), function(item) {
                if(item.appType == 10){
                    return;
                }
                //color text appDate
                let color = item.appDate.substring(9,10);
                if (color == '土') {//土
                    result.push(new vmbase.TextColor(item.appID,'appDate','saturdayCell'));
                }
                if (color == '日') {//日
                    result.push(new vmbase.TextColor(item.appID,'appDate','sundayCell'));
                }
                //fill color text input date
                let colorIn = item.inputDate.substring(9,10);
                if (colorIn == '土') {//土
                    result.push(new vmbase.TextColor(item.appID,'inputDate','saturdayCell'));
                }
                if (colorIn == '日') {//日
                    result.push(new vmbase.TextColor(item.appID,'inputDate','sundayCell'));
                }
             });
            return result;
        }
        reloadGridApproval(lstHidden: Array<any>, colorBackGr: any, isHidden: boolean) {

            var self = this;
            let widthAuto = isHidden == false ? 1175 : 1110;
            // widthAuto = screen.width - 35 >= widthAuto ? widthAuto : screen.width - 35;
            widthAuto = window.innerWidth >= 1280 ? window.innerWidth - 130 : 1100;

            var contentWidth = 340;
            character.restore('TableColumnWidth1' + __viewContext.user.companyId + __viewContext.user.employeeId).then((obj) => {
                    if(obj !== undefined && self.mode() === 1 && obj.appLstAtr === false && obj.cID === __viewContext.user.companyId && obj.sID === __viewContext.user.employeeId) {
                        contentWidth = obj.width;
                    } else {
                        contentWidth = widthAuto - 35 - 55 - 120 - 90 - 65- 157 - 120 - 75 - 95;
                    }
            }).then(() => {
                let columns = [
                    { headerText: getText('CMM045_49'), key: 'check', dataType: 'boolean', width: '35px', checkbox: {
                        visible: item => item.checkAtr === true,
                        applyToProperty: "check"
                    } },
                    { headerText: getText('CMM045_50'), key: 'details', width: '55px', button: {
                        text: getText('CMM045_50'),
                        click: (e) => {
                            let targetAppId = $(e.target).closest("td").data("app-id");
                            let lstAppId = self.items().map(app => app.appID);
                            // nts.uk.localStorage.setItem('UKProgramParam', 'a=1');
                            character.save('AppListExtractCondition', self.appListExtractConditionDto).then(() => {
								nts.uk.request.jump("/view/kaf/000/b/index.xhtml", { 'listAppMeta': lstAppId, 'currentApp': targetAppId });
							});
                        }
                    } },
                    { headerText: getText('CMM045_51'), key: 'applicantName', width: '120px' },
                    { headerText: getText('CMM045_52'), key: 'appType', width: '90px'},
                    { headerText: getText('CMM045_53'), key: 'prePostAtr', width: '65px', hidden: isHidden},
                    { headerText: getText('CMM045_54'), key: 'appDate', width: '157px'},
                    { headerText: getText('CMM045_55'), key: 'appContent', width: contentWidth},
                    { headerText: getText('CMM045_56'), key: 'inputDate', width: '120px'},
                    { headerText: getText('CMM045_57'), key: 'reflectionStatus', width: '75px', extraClassProperty: "appStatusName"},
                    { headerText: getText('CMM045_58'), key: 'opApprovalStatusInquiry', width: '95px' },
                ]
                let heightAuto = window.innerHeight >= 768 ? window.innerHeight - 357 : 272;
                // let heightAuto = window.innerHeight - 375 > 292 ? window.innerHeight - 375 : 292;
                this.setupGrid({
                    withCcg001: true,
                    width: widthAuto,
                    height: heightAuto,
                    columns: columns.filter(c => c.hidden !== true)
                });

                $("#app-resize").css("width", widthAuto);
            });

/*
            $("#grid1").ntsGrid({
                width: widthAuto,
                height: window.innerHeight - 330,
                dataSource: self.items(),
                primaryKey: 'appId',
                rowVirtualization: true,
                virtualization: true,
                hidePrimaryKey: true,
                rows: 8,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: getText('CMM045_49'), key: 'check', dataType: 'boolean', width: '35px',
                            showHeaderCheckbox: lstHidden.length < self.items().length, ntsControl: 'Checkbox',  hiddenRows: lstHidden},
                    { headerText: getText('CMM045_50'), key: 'details', dataType: 'string', width: '55px', unbound: false, ntsControl: 'Button' },
                    { headerText: getText('CMM045_51'), key: 'applicant', dataType: 'string', width: '120px' },
                    { headerText: getText('CMM045_52'), key: 'appName', dataType: 'string', width: '90px'},
                    { headerText: getText('CMM045_53'), key: 'appAtr', dataType: 'string', width: '65px', hidden: isHidden},
                    { headerText: getText('CMM045_54'), key: 'appDate', dataType: 'string', width: '157px'},
                    { headerText: getText('CMM045_55'), key: 'appContent', dataType: 'string', width: '341px'},
                    { headerText: getText('CMM045_56'), key: 'inputDate', dataType: 'string', width: '120px'},
                    { headerText: getText('CMM045_57'), key: 'appStatus', dataType: 'string', width: '75px'},
                    { headerText: getText('CMM045_58'), key: 'displayAppStatus', dataType: 'string', width: '95px' },
                    { headerText: 'ID', key: 'appId', dataType: 'string', width: '0px', ntsControl: 'Label', hidden: true }
                ],
                features: [{ name: 'Resizing' },
                    {
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: true
                    }
                ],
                 ntsFeatures:[
                    {
                        name: 'CellState',
                        rowId: 'rowId',
                        columnKey: 'columnKey',
                        state: 'state',
                        states: colorBackGr
                    },
                 ],
                ntsControls: [{ name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox' },
                    { name: 'Button', text: getText('CMM045_50'), controlType: 'Button', enable: true }],
            });

            $("#grid1").on("click", ".ntsButton", function(evt, ui) {
                let _this = $(this);
                let id = _this.parents('tr').data('id');
                //Bug #97203 - EA2540
//                let a = self.findDataModeAppByID(id, self.items());
//                let lstAppId = self.findListAppType(a.appType);
                let lstAppId = [];
                _.each(self.items(), function(app){
                    lstAppId.push(app.appId);
                });
                nts.uk.localStorage.setItem('UKProgramParam', 'a=1');
                nts.uk.request.jump("/view/kaf/000/b/index.xhtml", { 'listAppMeta': lstAppId, 'currentApp': id });
            });

            $("#grid1").setupSearchScroll("igGrid", true);
            */
        }
        /**
         * 休日出勤時間申請
         * kaf010 - appTYpe = 6
         * format data: holiday work before
         * ※申請モード、承認モード(事前)用レイアウト
         */
        /*formatHdWorkBf(app: vmbase.ApplicationDataOutput, masterInfo: vmbase.AppMasterInfo): vmbase.DataModeApp {
            let self = this;
            let empNameFull = masterInfo.inpEmpName == null ? masterInfo.empName : masterInfo.empName + getText('CMM045_230', [masterInfo.inpEmpName]);
            let applicant: string = masterInfo.workplaceName == '' ? empNameFull : masterInfo.workplaceName + '<br/>' + empNameFull;
            let prePost = app.prePostAtr == 0 ? '事前' : '事後';
            let prePostApp = masterInfo.checkAddNote == true ? prePost + getText('CMM045_101') : prePost;

            let a: vmbase.DataModeApp = new vmbase.DataModeApp(app.applicationID, app.applicationType, 'chi tiet', applicant,
                masterInfo.dispName, prePostApp, self.appDateColor(self.convertDateMDW(app.startDate), '',''),
                self.findContent(app.applicationID).content, self.inputDateColor(self.convertDateTime(app.inputDate), ''),
                app.reflectStatus, masterInfo.phaseStatus, masterInfo.statusFrameAtr, app.version, masterInfo.checkTimecolor,
                null, app.reflectPerState);
            return a;
        }*/
        /**
         * 残業申請
         * kaf005 - appType = 0
         * format data: over time before
         * ※申請モード、承認モード(事前)用レイアウト
         */
        /*formatOverTimeBf(app: vmbase.ApplicationDataOutput, masterInfo: vmbase.AppMasterInfo): vmbase.DataModeApp {
            let self = this;
            let empNameFull = masterInfo.inpEmpName == null ? masterInfo.empName : masterInfo.empName + getText('CMM045_230', [masterInfo.inpEmpName]);
//            let applicant: string = masterInfo.workplaceName + '<br/>' + empNameFull;
            let applicant: string = masterInfo.workplaceName == '' ? empNameFull : masterInfo.workplaceName + '<br/>' + empNameFull;
            let prePost = app.prePostAtr == 0 ? '事前' : '事後';
            let prePostApp = masterInfo.checkAddNote == true ? prePost + getText('CMM045_101') : prePost;
            let a: vmbase.DataModeApp = new vmbase.DataModeApp(app.applicationID, app.applicationType, 'chi tiet', applicant,
                masterInfo.dispName, prePostApp, self.appDateColor(self.convertDateMDW(app.startDate), '',''),
                self.findContent(app.applicationID).content, self.inputDateColor(self.convertDateTime(app.inputDate), ''),
                app.reflectStatus, masterInfo.phaseStatus,
                masterInfo.statusFrameAtr, app.version, masterInfo.checkTimecolor,null, app.reflectPerState);
            return a;
        }*/
        /**
         * ※承認モード(事後)用レイアウト
         * format data: over time after
         */
        /*formatHdWorkAf(app: vmbase.ApplicationDataOutput, masterInfo: vmbase.AppMasterInfo): vmbase.DataModeApp {
            let self = this;
            let empNameFull = masterInfo.inpEmpName == null ? masterInfo.empName : masterInfo.empName + getText('CMM045_230', [masterInfo.inpEmpName]);
            let applicant: string = masterInfo.workplaceName == '' ? empNameFull : masterInfo.workplaceName + '<br/>' + empNameFull;
            let prePost = app.prePostAtr == 0 ? '事前' : '事後';
            let prePostApp = masterInfo.checkAddNote == true ? prePost + getText('CMM045_101') : prePost;
            let a: vmbase.DataModeApp = new vmbase.DataModeApp(app.applicationID, app.applicationType, 'chi tiet', applicant,
                masterInfo.dispName, prePostApp, self.appDateColor(self.convertDateMDW(app.startDate), '',''),
                self.findContent(app.applicationID).content, self.inputDateColor(self.convertDateTime(app.inputDate), ''),
                app.reflectStatus, masterInfo.phaseStatus, masterInfo.statusFrameAtr, app.version, masterInfo.checkTimecolor,
                null, app.reflectPerState);
            return a;
        }*/

        /**
         * ※承認モード(事後)用レイアウト
         * format data: over time after
         */
        /*formatOverTimeAf(app: vmbase.ApplicationDataOutput, masterInfo: vmbase.AppMasterInfo): vmbase.DataModeApp {
            let self = this;
            let empNameFull = masterInfo.inpEmpName == null ? masterInfo.empName : masterInfo.empName + getText('CMM045_230', [masterInfo.inpEmpName]);
            let applicant: string = masterInfo.workplaceName == '' ? empNameFull : masterInfo.workplaceName + '<br/>' + empNameFull;
            let prePost = app.prePostAtr == 0 ? '事前' : '事後';
            let prePostApp = masterInfo.checkAddNote == true ? prePost + getText('CMM045_101') : prePost;
            let a: vmbase.DataModeApp = new vmbase.DataModeApp(app.applicationID, app.applicationType, 'chi tiet', applicant,
                masterInfo.dispName, prePostApp, self.appDateColor(self.convertDateMDW(app.startDate), '',''),
                self.findContent(app.applicationID).content, self.inputDateColor(self.convertDateTime(app.inputDate), ''),
                app.reflectStatus, masterInfo.phaseStatus, masterInfo.statusFrameAtr, app.version, masterInfo.checkTimecolor,
                null, app.reflectPerState);
            return a;
        }*/
        /**
         * 直行直帰申請
         * kaf009 - appType = 4
         */
        /*formatGoBack(app: vmbase.ApplicationDataOutput, masterInfo: vmbase.AppMasterInfo): vmbase.DataModeApp {
            let self = this;
            let empNameFull = masterInfo.inpEmpName == null ? masterInfo.empName : masterInfo.empName + getText('CMM045_230', [masterInfo.inpEmpName]);
            let applicant: string = masterInfo.workplaceName == '' ? empNameFull : masterInfo.workplaceName + '<br/>' + empNameFull;
            let prePost = app.prePostAtr == 0 ? '事前' : '事後';
            let prePostApp = masterInfo.checkAddNote == true ? prePost + getText('CMM045_101') : prePost;
            let a: vmbase.DataModeApp = new vmbase.DataModeApp(app.applicationID, app.applicationType, 'chi tiet', applicant,
                masterInfo.dispName, prePostApp, self.appDateColor(self.convertDateMDW(app.startDate), '',''),
                self.findContent(app.applicationID).content, self.inputDateColor(self.convertDateTime(app.inputDate), ''),
                app.reflectStatus, masterInfo.phaseStatus, masterInfo.statusFrameAtr, app.version, masterInfo.checkTimecolor,
                null, app.reflectPerState);
            return a;
        }*/
        /**
         * 勤務変更申請
         * kaf007 - appType = 2
         */
        /*formatWorkChange(app: vmbase.ApplicationDataOutput, masterInfo: vmbase.AppMasterInfo): vmbase.DataModeApp {
            let self = this;
            let empNameFull = masterInfo.inpEmpName == null ? masterInfo.empName : masterInfo.empName + getText('CMM045_230', [masterInfo.inpEmpName]);
            let applicant: string = masterInfo.workplaceName == '' ? empNameFull : masterInfo.workplaceName + '<br/>' + empNameFull;
            let prePost = app.prePostAtr == 0 ? '事前' : '事後';
            let prePostApp = masterInfo.checkAddNote == true ? prePost + getText('CMM045_101') : prePost;
            let dateRange = app.startDate == app.endDate ? self.appDateColor(self.convertDateMDW(app.applicationDate), '','') :
                self.appDateRangeColor(self.convertDateMDW(app.startDate), self.convertDateMDW(app.endDate));
            let a: vmbase.DataModeApp = new vmbase.DataModeApp(app.applicationID, app.applicationType, 'chi tiet', applicant,
                masterInfo.dispName, prePostApp, dateRange, self.findContent(app.applicationID).content,
                self.inputDateColor(self.convertDateTime(app.inputDate), ''), app.reflectStatus, masterInfo.phaseStatus,
                masterInfo.statusFrameAtr, app.version, masterInfo.checkTimecolor, null, app.reflectPerState);
            return a;
        }*/
        /**
         * 休暇申請
         * kaf006 - appType = 1
         * DOING
         */
        /*formatAbsence(app: vmbase.ApplicationDataOutput, masterInfo: vmbase.AppMasterInfo): vmbase.DataModeApp {
            let self = this;
            let empNameFull = masterInfo.inpEmpName == null ? masterInfo.empName : masterInfo.empName + getText('CMM045_230', [masterInfo.inpEmpName]);
            let applicant: string = masterInfo.workplaceName == '' ? empNameFull : masterInfo.workplaceName + '<br/>' + empNameFull;
            let prePost = app.prePostAtr == 0 ? '事前' : '事後';
            let prePostApp = masterInfo.checkAddNote == true ? prePost + getText('CMM045_101') : prePost;
            let dateRange = app.startDate == app.endDate ? self.appDateColor(self.convertDateMDW(app.applicationDate), '','') :
                self.appDateRangeColor(self.convertDateMDW(app.startDate), self.convertDateMDW(app.endDate));
            let a: vmbase.DataModeApp = new vmbase.DataModeApp(app.applicationID, app.applicationType, 'chi tiet', applicant,
                masterInfo.dispName, prePostApp, dateRange, self.findContent(app.applicationID).content,
                self.inputDateColor(self.convertDateTime(app.inputDate), ''), app.reflectStatus, masterInfo.phaseStatus,
                masterInfo.statusFrameAtr, app.version, masterInfo.checkTimecolor, null, app.reflectPerState);
            return a;
        }*/
        /**
         * 振休振出申請
         * kaf011 - appType = 10
         */
        /*formatCompltLeave(app: vmbase.ApplicationDataOutput, masterInfo: vmbase.AppMasterInfo): vmbase.DataModeApp{
            let self = this;
            let empNameFull = masterInfo.inpEmpName == null ? masterInfo.empName : masterInfo.empName + getText('CMM045_230', [masterInfo.inpEmpName]);
            let applicant: string = masterInfo.workplaceName == '' ? empNameFull : masterInfo.workplaceName + '<br/>' + empNameFull;

            //振出 rec typeApp = 1
            //振休 abs typeApp = 0
            let prePost = app.prePostAtr == 0 ? '事前' : '事後';
            let appDate = self.appDateColor(self.convertDateMDW(app.applicationDate), '','');
            let inputDate = self.inputDateColor(self.convertDateTime(app.inputDate), '');

            let a: vmbase.DataModeApp = new vmbase.DataModeApp(app.applicationID, app.applicationType, 'chi tiet', applicant,
                masterInfo.dispName, prePost, appDate, self.findContent(app.applicationID).content, inputDate,
                app.reflectStatus, masterInfo.phaseStatus, masterInfo.statusFrameAtr, app.version, masterInfo.checkTimecolor,
                null, app.reflectPerState);
            return a;
        }*/
        /**
         * 振休振出申請
         * 同期
         * kaf011 - appType = 10
         */
        /*formatCompltSync(app: vmbase.ApplicationDataOutput, complt: vmbase.AppAbsRecSyncData, masterInfo: vmbase.AppMasterInfo): vmbase.DataModeApp{
            let self = this;
            let empNameFull = masterInfo.inpEmpName == null ? masterInfo.empName : masterInfo.empName + getText('CMM045_230', [masterInfo.inpEmpName]);
            let applicant: string = masterInfo.workplaceName == '' ? empNameFull : masterInfo.workplaceName + '<br/>' + empNameFull;

            let prePost = app.prePostAtr == 0 ? '事前' : '事後';

            let appDateAbs = '';
            let appDateRec = '';
            let inputDateAbs = '';
            let inputDateRec = '';
            //振出 rec typeApp = 1
            //振休 abs typeApp = 0
            if(complt.typeApp == 0){
                appDateAbs = self.appDateColor(self.convertDateMDW(app.applicationDate), 'abs','');
                appDateRec = self.appDateColor(self.convertDateMDW(complt.appDateSub), 'rec','');
                inputDateAbs = self.inputDateColor(self.convertDateTime(app.inputDate), 'abs');
                inputDateRec = self.inputDateColor(self.convertDateTime(app.inputDate), 'rec');
            }else{
                appDateRec = self.appDateColor(self.convertDateMDW(app.applicationDate), 'rec','');
                appDateAbs = self.appDateColor(self.convertDateMDW(complt.appDateSub), 'abs','');
                inputDateRec = self.inputDateColor(self.convertDateTime(app.inputDate), 'rec');
                inputDateAbs = self.inputDateColor(self.convertDateTime(app.inputDate), 'abs');
            }
            let a: vmbase.DataModeApp = new vmbase.DataModeApp(app.applicationID, app.applicationType, 'chi tiet', applicant,
                masterInfo.dispName, prePost, appDateRec + '<br/>' + appDateAbs, self.findContent(app.applicationID).content,
                inputDateRec + '<br/>' + inputDateAbs,
                '<div class = "rec" >' + app.reflectStatus + '</div>' + '<br/>' + '<div class = "abs" >' + app.reflectStatus + '</div>',
                masterInfo.phaseStatus, masterInfo.statusFrameAtr, app.version, masterInfo.checkTimecolor, complt.appSubID, app.reflectPerState);
            return a;
        }*/
        inputDateColor_Old(input: string, classApp: string): string{
            let inputDate = '<div class = "' + classApp + '" >' + input + '</div>';
            //fill color text input date
            let colorIn = input.substring(11,12);
            if (colorIn == '土') {//土
                inputDate = '<div class = "saturdayCell ' + classApp + '" >' + input + '</div>';
            }
            if (colorIn == '日') {//日
                inputDate = '<div class = "sundayCell ' + classApp + '" >' + input + '</div>';
            }
            return inputDate;
        }
        //ver41
        inputDateColor(input: string, classApp: string): string{
            let inputDate = '<div class = "' + classApp + '" >' + input + '</div>';
            //fill color text input date
            let a = input.split("(")[1];
            let colorIn = a.substring(0,1);
            if (colorIn == '土') {//土
                inputDate = '<div class = "saturdayCell ' + classApp + '" >' + input + '</div>';
            }
            if (colorIn == '日') {//日
                inputDate = '<div class = "sundayCell ' + classApp + '" >' + input + '</div>';
            }
            return inputDate;
        }
        appDateColor(date: string, classApp: string, priod: string): string{
            let appDate = '<div class = "' + classApp + '" >' + date + priod + '</div>';;
            //color text appDate
            let a = date.split("(")[1];
            let color = a.substring(0,1);
            if (color == '土') {//土
                appDate = '<div class = "saturdayCell  ' + classApp + '" >' + date + priod +'</div>';
            }
            if (color == '日') {//日
                appDate = '<div class = "sundayCell  ' + classApp + '" >' + date + priod + '</div>';
            }
            return appDate;
        }
        //doi ung theo y amid-mizutani さん
        appDateRangeColor(startDate: string, endDate: string): string{
            let sDate = '<div class = "dateRange" >' + startDate + '</div>';;
            let eDate =  '<div class = "dateRange" >' + endDate + '</div>';
            //color text appDate
            let a = startDate.split("(")[1];
            let b = endDate.split("(")[1];
            let color1 = a.substring(0,1);
            if (color1 == '土') {//土
                sDate = '<div class = "saturdayCell  dateRange" >' + startDate +  '</div>';
            }
            if (color1 == '日') {//日
                sDate = '<div class = "sundayCell  dateRange" >' + startDate + '</div>';
            }
            let color2 = b.substring(0,1);
            if (color2 == '土') {//土
                eDate = '<div class = "saturdayCell  dateRange" >' + endDate + '</div>';
            }
            if (color2 == '日') {//日
                eDate = '<div class = "sundayCell  dateRange" >' + endDate +  '</div>';
            }
            return sDate + '<div class = "dateRange" >' + '－' +  '</div>' +  eDate;
        }
        /**
         * map data -> fill in grid list
         */
        /*mapData(lstApp: Array<vmbase.ApplicationDataOutput>, lstMaster: Array<vmbase.AppMasterInfo>, lstCompltLeave: Array<vmbase.AppAbsRecSyncData>): Array<vmbase.DataModeApp> {
            let self = this;
            let lstData: Array<vmbase.DataModeApp> = [];
            _.each(lstApp, function(app: vmbase.ApplicationDataOutput) {
                let masterInfo = self.findMasterInfo(lstMaster, app.applicationID);
                let data: vmbase.DataModeApp;
                if (app.applicationType == 0) {//over time
                    if (self.mode() == 1 && app.prePostAtr == 1) {
                        data = self.formatOverTimeAf(app, masterInfo);
                    } else {
                        data = self.formatOverTimeBf(app, masterInfo);
                    }
                }
                if (app.applicationType == 4) {//goback
                    data = self.formatGoBack(app, masterInfo);
                }
                if(app.applicationType == 6){//holiday work
                    if(self.mode() == 1 && app.prePostAtr == 1){
                        data = self.formatHdWorkAf(app, masterInfo);
                    }else{
                        data = self.formatHdWorkBf(app, masterInfo);
                    }
                }
                if(app.applicationType == 2){//work change
                    data = self.formatWorkChange(app, masterInfo);
                }
                if(app.applicationType == 1){//absence
                    data = self.formatAbsence(app, masterInfo);
                }
                if(app.applicationType == 10){//Complement Leave
                    let complt = self.checkSync(app.applicationID, lstCompltLeave);
                    if(complt !== undefined){
                        data = self.formatCompltSync(app, complt, masterInfo);
                    }else{
                        data = self.formatCompltLeave(app, masterInfo);
                    }
                }
                lstData.push(data);
            });
            return lstData;
        }*/
        /**
         * find application holiday work by id
         */
        checkSync(appId: string, lstCompltLeave: Array<vmbase.AppAbsRecSyncData>):vmbase.AppAbsRecSyncData{
            return _.find(lstCompltLeave, function(complt){
                return complt.appMainID == appId;
        });
        }
        /**
         * find master info by id
         */
        findMasterInfo(lstMaster: Array<vmbase.AppMasterInfo>, appId: string) {
            return _.find(lstMaster, function(master) {
                return master.appID == appId;
            });
        }
        //yyyy/MM/dd(W)
        convertDate(date: string) {
            let a: number = moment(date, 'YYYY/MM/DD').isoWeekday();
            switch (a) {
                case 1://Mon
                    return date + '(月)';
                case 2://Tue
                    return date + '(火)';
                case 3://Wed
                    return date + '(水)';
                case 4://Thu
                    return date + '(木)';
                case 5://Fri
                    return date + '(金)';
                case 6://Sat
                    return date + '(土)';
                default://Sun
                    return date + '(日)';
            }
        }
        //MM/dd(W) ver24
        convertDateMDW(date: string) {
            let a: number = moment(date, 'YYYY/MM/DD').isoWeekday();
            let toDate = moment(date, 'YYYY/MM/DD').toDate();
            let dateMDW = (toDate.getMonth()+1) + '/'+ toDate.getDate();
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
        //Short_MD
        convertDateShort_MD(date: string) {
            let a: number = moment(date, 'YYYY/MM/DD').isoWeekday();
            let toDate = moment(date, 'YYYY/MM/DD').toDate();
            let dateMDW = (toDate.getMonth()+1) + '/'+ toDate.getDate();
            return dateMDW;
        }
        //yyyy/MM/dd(W) hh:mm
        convertDateTime_Old(dateTime: string) {
            let a: number = moment(dateTime, 'YYYY/MM/DD hh:mm').isoWeekday();
            let date = dateTime.split(" ")[0];
            let time = dateTime.split(" ")[1];
            return this.convertDate(date) + ' ' + time;
        }
        //ver41
        //Short_MDW  hh:mm : MM/dd(W) hh:mm
        convertDateTime(dateTime: string) {
            let a: number = moment(dateTime, 'YYYY/MM/DD hh:mm').isoWeekday();
            let date = dateTime.split(" ")[0];
            let time = dateTime.split(" ")[1];
            return this.convertDateMDW(date) + ' ' + time;
        }
        /**
         * when click button 検索
         */
        /*filter() {
            block.invisible();
            if (nts.uk.ui.errors.hasError()) {
                block.clear();
                return;
            }
            let self = this;
            //check filter
            //check startDate
            if (self.dateValue().startDate == null || self.dateValue().startDate == '') {//期間開始日付または期間終了日付が入力されていない
                $('.ntsDatepicker.nts-input.ntsStartDatePicker.ntsDateRange_Component').ntsError('set', {messageId:"Msg_359"});
                block.clear();
                return;
            }
            //check endDate
            if (self.dateValue().endDate == null || self.dateValue().endDate == '') {//期間開始日付または期間終了日付が入力されていない
                $('.ntsDatepicker.nts-input.ntsEndDatePicker.ntsDateRange_Component').ntsError('set', {messageId:"Msg_359"});
                block.clear();
                return;
            }
            if (self.mode() == 1 && self.selectedIds().length == 0) {//承認状況のチェックの確認
                nts.uk.ui.dialog.error({ messageId: "Msg_360" });
                block.clear();
                return;
            }
            let condition: vmbase.AppListExtractConditionDto = new vmbase.AppListExtractConditionDto(self.dateValue().startDate, self.dateValue().endDate, self.mode(),
                self.selectedCode(), self.findcheck(self.selectedIds(), 1), self.findcheck(self.selectedIds(), 2), self.findcheck(self.selectedIds(), 3),
                self.findcheck(self.selectedIds(), 4), self.findcheck(self.selectedIds(), 5), self.findcheck(self.selectedIds(), 6), 0, self.lstSidFilter(), '');
            let param = {   condition: condition,
                            spr: false,
                            extractCondition: 0,
                            device: 0,
                            lstAppType: []
                        }
            service.getApplicationList(param).done(function(data) {
                self.lstContentApp([]);
                self.lstContentApp(data.lstContentApp);
                //reset data
                self.lstAppCommon([]);
                self.lstAppMaster([]);
                self.lstApp([]);
                self.lstListAgent([]);
                self.lstAppCompltSync([]);
                //luu
                character.save('AppListExtractCondition', condition);
                _.each(data.lstApp, function(app) {
                    self.lstAppCommon.push(new vmbase.ApplicationDataOutput(app.applicationID, app.prePostAtr, app.inputDate,
                        app.enteredPersonSID, app.applicationDate, app.applicationType, app.applicantSID,
                        app.reflectPerState, app.startDate, app.endDate, app.version, app.reflectStatus));
                });
                _.each(data.lstMasterInfo, function(master) {
                    self.lstAppMaster.push(new vmbase.AppMasterInfo(master.appID, master.appType, master.dispName, master.empName, master.inpEmpName, master.workplaceName,
                        master.statusFrameAtr, master.phaseStatus, master.checkAddNote, master.checkTimecolor, master.detailSet));
                });
                self.itemApplication([]);
                _.each(data.lstAppInfor, function(appInfo){
                    self.itemApplication.push(new vmbase.ChoseApplicationList(appInfo.appType, appInfo.appName));
                });
                self.lstListAgent([]);
                _.each(data.lstAgent, function(agent){
                    self.lstListAgent.push(new vmbase.ApproveAgent(agent.appID, agent.agentId));
                });
                _.each(data.lstSyncData, function(complt){
                    self.lstAppCompltSync.push(new vmbase.AppAbsRecSyncData(complt.typeApp, complt.appMainID, complt.appSubID, complt.appDateSub));
                });
                let lstData = self.mapData(self.lstAppCommon(), self.lstAppMaster(), self.lstAppCompltSync());
                self.lstApp(lstData);
                //check list app new exist selectedCode???
                let check = self.findExist();
                if(check === undefined){
                    self.selectedCode(-1);
                }
                if (self.selectedCode() != -1) {
                    self.filterByAppType(self.selectedCode());
                } else {
                    self.items(lstData);
                    //mode approval - count
                    if (data.appStatusCount != null) {
                        self.approvalCount(new vmbase.ApplicationStatus(data.appStatusCount.unApprovalNumber, data.appStatusCount.approvalNumber,
                            data.appStatusCount.approvalAgentNumber, data.appStatusCount.cancelNumber, data.appStatusCount.remandNumner,
                            data.appStatusCount.denialNumber));
                    }

                    if (self.mode() == 1) {
                        $("#grid1").ntsGrid("destroy");
                        let colorBackGr = self.fillColorbackGrAppr();
                        let lstHidden: Array<any> = self.findRowHidden(self.items());
                        self.reloadGridApproval(lstHidden,colorBackGr, self.isHidden());
                    } else {
                        let colorBackGr = self.fillColorbackGr();
                        $("#grid2").ntsGrid("destroy");
                        self.reloadGridApplicaion(colorBackGr, self.isHidden());
                    }
                }
                block.clear();
            }).fail(() => {
                block.clear();
            });
        }*/
        /*findExist(): any{
            let self = this;
            return _.find(self.itemApplication(), function(item){
                return item.appId == self.selectedCode();
            });
        }*/
        /**
         * find row hidden
         */
        findRowHidden(lstItem: Array<vmbase.DataModeApp>): any{
            let lstHidden = []
            _.each(lstItem, function(item){
                if(item.appStatusNo != 5){
                    lstHidden.push(item.appID);
                }
            });
            return lstHidden;
        }
        /**
         * find check box
         */
        findcheck(selectedIds: Array<any>, idCheck: number): boolean {
            let check = false;
            _.each(selectedIds, function(id) {
                if (id == idCheck) {
                    check = true;
                }
            });
            return check;
        }
        /**
         * When click button 承認
         */
        /*approval() {
            block.invisible();
            let self = this;
            let data = null;
            let lstApp = [];
            _.each(self.items(), function(item) {
                if (item.check && item.checkAtr) {
                    if(item.appType == 10 && item.appIdSub != null){
                        lstApp.push({ appId: item.appId, version: item.version });
                        lstApp.push({ appId: item.appIdSub, version: item.version });
                    }else{
                        lstApp.push({ appId: item.appId, version: item.version });
                    }
                }
            });
            if(lstApp.length == 0){
                block.clear();
                return;
            }
            service.approvalListApp(lstApp).done(function(data) {
                if(data.length > 0){
                    service.reflectListApp(data);
                }
                nts.uk.ui.dialog.info({ messageId: "Msg_220" });
                self.filter();
                block.clear();
            }).fail(function(res) {
                block.clear();
                nts.uk.ui.dialog.alertError({ messageId: res.messageId });
            });
        }*/
        /**
         * When select combo box 申請種類
         */
        /*filterByAppType(appType: number) {
            let self = this;
            let paramOld = null;
            let paramNew = null;
            character.restore("AppListExtractCondition").done((obj) => {
                 if (obj !== undefined) {
                    paramOld = obj;
                }
            });
            if(paramOld != null){
                paramNew = paramOld.setAppType(appType);
            }else{
                paramNew = new vmbase.AppListExtractConditionDto(self.dateValue().startDate, self.dateValue().endDate, self.mode(),
                self.selectedCode(), self.findcheck(self.selectedIds(), 1), self.findcheck(self.selectedIds(), 2), self.findcheck(self.selectedIds(), 3),
                self.findcheck(self.selectedIds(), 4), self.findcheck(self.selectedIds(), 5), self.findcheck(self.selectedIds(), 6), 0, self.lstSidFilter(), '');
            }
            //luu
                character.save('AppListExtractCondition', paramNew);
            if (appType == -1) {//全件表示
                self.items(self.lstApp());
            } else {
                let lstAppFitler: Array<vmbase.DataModeApp> = _.filter(self.lstApp(), function(item) {
                    return item.appType == appType;
                });
                self.items([]);
                self.items(lstAppFitler);
            }
            if (self.mode() == 1) {
                self.approvalCount(self.countStatus(self.items()));
                if($("#grid1").data("igGrid") !== undefined){
                    $("#grid1").ntsGrid("destroy");
                }
                let colorBackGr = self.fillColorbackGrAppr();
                let lstHidden: Array<any> = self.findRowHidden(self.items());
                self.reloadGridApproval(lstHidden,colorBackGr, self.isHidden());
            } else {
                if($("#grid2").data("igGrid") !== undefined){
                    $("#grid2").ntsGrid("destroy");
                }
                let colorBackGr = self.fillColorbackGr();
                self.reloadGridApplicaion(colorBackGr, self.isHidden());
            }
        }*/
        /**
         * count status when filter by appType
         */
        countStatus(lstApp: Array<vmbase.DataModeApp>): vmbase.ApplicationStatus{
            var self = this;
            let unApprovalNumber = 0;
            let approvalNumber = 0;
            let approvalAgentNumber = 0;
            let cancelNumber = 0;
            let remandNumner = 0;
            let denialNumber = 0;
            _.each(lstApp, function(app){
                let add = self.checkSync(app.appID, self.lstAppCompltSync()) !== undefined ? 2 : 1;
                if(app.appStatusNo == 5){ unApprovalNumber += add; }//UNAPPROVED:5
                if(app.appStatusNo == 4){//APPROVED: 4
                    let agent = self.findAgent(app.appID);
                    if(agent != undefined && agent.agentId != null && agent.agentId != '' && agent.agentId.match(/^\s+$/) == null){
                        approvalAgentNumber += add;
                    }else{
                        approvalNumber += add;
                    }
                }
                if(app.appStatusNo == 3){ cancelNumber += add; }//CANCELED: 3
                if(app.appStatusNo == 2){ remandNumner += add; }//REMAND: 2
                if(app.appStatusNo == 1){ denialNumber += add; }//DENIAL: 1
            })
            return new vmbase.ApplicationStatus(unApprovalNumber, approvalNumber,
                approvalAgentNumber, cancelNumber, remandNumner,denialNumber);
        }
        findAgent(appId: string): any{
            return _.find(this.lstListAgent(), function(agent){
                return agent.appID == appId;
            });
        }
        convertTime_Short_HM(time: number): string {
            let hh = Math.floor(time / 60);
            let min1 = Math.floor(time % 60);
            let min = '';
            if (min1 >= 10) {
                min = min1.toString();
            } else {
                min = '0' + min1;
            }
            return hh + ':' + min;
        }
        //find content app
        findContent(appId: string): any{
            let self = this;
            return _.find(self.lstContentApp(), function(app) {
                return app.appId == appId;
            });
        }

        approveAll() {
            console.log("Approve all");
        }

        print(params: any) {
            let self = this;
            let lstApp = self.appListInfo,
            programName = nts.uk.ui._viewModel.kiban.programName().replace('CMM045A ', '');
            lstApp.appLst = ko.toJS(self.items);
            lstApp.displaySet.startDateDisp = self.appListExtractConditionDto.periodStartDate;
            lstApp.displaySet.endDateDisp = self.appListExtractConditionDto.periodEndDate;
			block.invisible();
            const command = { appListAtr: self.appListAtr, lstApp: lstApp, programName: programName }
            service.print(command).always(() => { 
				block.clear(); 
				$('#daterangepicker .ntsEndDatePicker').focus();
			});
            
        }

        // getNtsFeatures(): Array<any> {
        //     let self = this;

        //     var features = [
        //         { name: 'TextColor',
        //             columns: [
        //                 {
        //                     key: 'inputDate',
        //                     parse: value => { return value; },
        //                     map: (content: String) => {
        //                         if(content.includes("土")) {
        //                             return "#0000ff";
        //                         }
        //                         if(content.includes("日")) {
        //                             return "#ff0000";
        //                         }
        //                     }
        //                 }
        //             ]
        //     }
        //     ];

        //     return features;
        // }


		appListApprove(isApprovalAll: boolean) {
			const self = this;
			let	msgConfirm = '';
			if(isApprovalAll) {
				let checkBoxList = $("#app-grid-container").find(".nts-fixed-body-wrapper tbody").find("tr").find("td.check").find("span");
				_.each(checkBoxList, checkbox => {
					let appID = $(checkbox).closest("td").data("app-id"),
						currentItem = _.find(self.items(), item => item.appID == appID);
					if(!_.isUndefined(currentItem)) {
						if(!currentItem.check) {
							checkbox.click();
						}
					}
				});
				msgConfirm = 'Msg_1551';
			} else {
				msgConfirm = 'Msg_1549';
			}
			nts.uk.ui.dialog.confirm({ messageId: msgConfirm}).ifYes(() => {
				block.invisible();
				let listOfApplicationCmds = [];
				_.each(self.items(), function(item) {
					// 対象の申請が未承認の申請の場合
					if(!item.checkAtr) {
						return;
					}
					// INPUT「一括承認」＝True
					if(!isApprovalAll) {
						if(!item.check)	{
							return;
						}
					}
					if(item.appType == 10 && item.appIdSub != null){
	                    listOfApplicationCmds.push({ appId: item.appID, version: item.version });
	                    listOfApplicationCmds.push({ appId: item.appIdSub, version: item.version });
	                }else{
	                    listOfApplicationCmds.push(item);
	                }
	            });
				if(_.isEmpty(listOfApplicationCmds)) {
					block.clear();
					return;
				}
				let device = 0,
					command = { isApprovalAll, device, listOfApplicationCmds };
				service.approveCheck(command).then((data: any) => {
					if(data) {
						let comfirmData = [];
						_.each(Object.keys(data.successMap), (dataAppID: any) => {
							let obj = _.find(listOfApplicationCmds, o => o.appID == dataAppID);
							if(!_.isUndefined(obj)) {
								comfirmData.push(obj);
							}
						});
						return service.approverAfterConfirm(comfirmData);
					}
				}).then((data: any) => {
					if(data) {
						let isInfoDialog = true,
							displayMsg = "";
						if(!_.isEmpty(data.successMap)) {
							displayMsg += nts.uk.resource.getMessage('Msg_220') + "\n";
						} else {
							isInfoDialog = false;
						}
						if(!_.isEmpty(data.failMap)) {
							if(isInfoDialog) {
								displayMsg += nts.uk.resource.getMessage('Msg_1726');
							} else {
								displayMsg += nts.uk.resource.getMessage('Msg_1725');
							}
							let itemFailMap = _.filter(listOfApplicationCmds, item => _.includes(Object.keys(data.failMap), item.appID));
							_.each(itemFailMap, item => {
								let appInfo = _.find(self.appListExtractConditionDto.opListOfAppTypes, o => o.appType == item.appType),
									appName = "";
								if(!_.isUndefined(appInfo)) {
									appName = appInfo.appName;
								}
								displayMsg += "\n " + item.applicantName  + " " + item.appDate + " " + appName + ": " + data.failMap[item.appID];
							});
						}
						if(isInfoDialog) {
							nts.uk.ui.dialog.info(displayMsg).then(() => {$('#daterangepicker .ntsEndDatePicker').focus()});
						} else {
						 	nts.uk.ui.dialog.alertError(displayMsg).then(() => {$('#daterangepicker .ntsEndDatePicker').focus()});
						}
						return data;
					}
	            }).then((data) => {
					if(!_.isEmpty(data.successMap)) {
						return service.findByPeriod(self.appListExtractConditionDto);
					}
				}).then((data: any) => {
					if(data) {
						return self.reload(data.appListExtractCondition, data.appListInfo);
					}
				}).always(() => {
                    block.clear();
                    $('#daterangepicker .ntsEndDatePicker').focus();
                });
			});
		}
    }
}
