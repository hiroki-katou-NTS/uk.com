module nts.uk.at.view.ksm007.a {

    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import resource = nts.uk.resource;

    export class ScreenModel {
        options: Option;
        currentIds: KnockoutObservable<any> = ko.observable(null);
        currentCodes: KnockoutObservable<any> = ko.observable([]);
        currentNames: KnockoutObservable<any> = ko.observable([]);
        workplaceGroupList: KnockoutObservable<any> = ko.observable([]);
        registerForm: RegisterForm = ko.observable(new RegisterForm());
        constructor() {
            let self = this;
            self.options = {
                itemList: self.workplaceGroupList,
                currentCodes: self.currentCodes,
                currentNames: self.currentNames,
                currentIds: self.currentIds,
                multiple: false,
                tabindex: 2,
                isAlreadySetting: false,
                showEmptyItem: false,
                reloadData: ko.observable(''),
                height: 400,
                selectedMode: 1
            };

            self.currentIds.subscribe((val) => {
                nts.uk.ui.errors.clearAll();
                if(val && val.length > 0) {
                    service.getWorkplaceGroupInfo(val).done((res) => {
                        // data server return list format
                        if(res.workplaces && res.workplaces[0]) {
                            self.registerForm().bindData(res.workplaces[0]);
                        }
                        service.getWorkplaceByGroup(val).done((wkps) => {
                            self.getAndBindWorkplaceInfo(wkps, new Date());
                        });
                    })
                }
            });

            if(self.workplaceGroupList().length === 0) {
                self.createNew();
            }

        }

        startPage(): JQueryPromise<any> {
            let dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        register() {
            let self = this;
            self.registerForm().trimData();
            
            $(".nts-input").trigger("validate");

            if(self.registerForm().workplaces().length === 0) {
                $('#workplace-list').ntsError('set', resource.getMessage("MsgB_2", resource.getText('Com_Workplace')), "MsgB_2");
            }

            if (nts.uk.ui.errors.hasError()) {
				return;
            }
            
            if(self.registerForm().newMode()) {
                service.registerWorkplaceGroup(self.registerForm().convertToCommand())
                .done((res)=> {
                    self.checkWorkplaceGroupRegisterResult(res)
                    .done(() => {
                        self.options.reloadData.valueHasMutated();
                        self.options.currentIds(res.wkpGrId);
                    });
                }).fail((res) => {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                }).always(function () {
                    nts.uk.ui.block.clear();
                });
            } else {
                service.updateWorkplaceGroup(self.registerForm().convertToCommand(self.currentIds[0]))
                .done((res)=> {
                    self.checkWorkplaceGroupRegisterResult(res)
                    .done(() => {
                        self.options.reloadData.valueHasMutated();
                        self.options.currentIds(res.wkpGrId);
                        self.options.currentIds.valueHasMutated();
                    });
                }).fail((res) => {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                }).always(function () {
                    nts.uk.ui.block.clear();
                });
            }
        }

        checkWorkplaceGroupRegisterResult(res) {
            let dfd = $.Deferred();
            dfd.resolve();
            
            let lstWKPID = res.lstWKPID;
            let resultProcess = res.resultProcess;
            let listWorkplaceInfo = res.listWorkplaceInfo;
            let bundledErrors = [];
            for (let idx in lstWKPID) {
                console.log(lstWKPID[idx]);
                if(resultProcess[idx].workplaceReplacement == "BELONGED_ANOTHER") {
                    let info = _.find(listWorkplaceInfo, (wkp) => {return wkp.workplaceId == lstWKPID[idx]; });
                    if (info) {
                        bundledErrors.push({
                            message: info.workplaceCode + ' ' + info.workplaceName + ' ' + info.genericName,
                            messageId: "Msg_1630",
                            supplements: {}
                        });
                    }
                }
            }
            if( bundledErrors.length > 0) {
               nts.uk.ui.dialog.bundledErrors({ errors: bundledErrors }).then(() => {
                   dfd.resolve();
               });
            } else {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                    dfd.resolve();
                });
            }
            
            return dfd.promise();
        }

        deleteWkpGroup() {
            nts.uk.ui.block.invisible();
			let self = this;
            let param = {wkpGrID: self.currentIds()};
            
			nts.uk.ui.dialog.confirm({ messageId: "Msg_18" })
				.ifYes(() => {
					let i = _.findIndex(self.workplaceGroupList(), x => { return x.id == self.currentIds() });
					let nextSelectedCode;
					if (self.workplaceGroupList().length == 1) {
						nextSelectedCode = '';
					} else if (i === 0) {
						nextSelectedCode = self.workplaceGroupList()[1].id;
					} else if (i === (self.workplaceGroupList().length - 1)) {
						nextSelectedCode = self.workplaceGroupList()[self.workplaceGroupList().length - 2].id;
					} else {
						nextSelectedCode = self.workplaceGroupList()[i + 1].id;
					}
					service.deleteWorkplaceGroup(param).done(() => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" })
                        .then(() => {
                            self.options.reloadData.valueHasMutated();
                            if(self.workplaceGroupList().length == 1) {
                                self.createNew();
                            } else {
                                self.currentIds(nextSelectedCode);
                            }
                        });
							
					}).fail((res) => {
						nts.uk.ui.dialog.alertError({ messageId: res.messageId });
					}).always(function () {
						nts.uk.ui.block.clear();
					});
				}).ifNo(() => {
					nts.uk.ui.block.clear();
				});
            
        }

        removeWorkplace() {
            let self = this;
            if(self.registerForm().workplaces().length === 0) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_1628" });
                return;
            }
            self.registerForm().removeWorkplace();
        }

        createNew() {
            let self = this;
            self.registerForm().clearData();
            $('#requiredCode').focus();
            self.currentIds([]);
        }

        /**
         * open dialog CDL008
         * chose work place
         * @param baseDate, isMultiple, workplaceId
         * @return workplaceId
         */
        openDialogCDL008() {
            let self = this;
            block.grayout();
            setShared('inputCDL008', { selectedCodes: self.registerForm().selectedWorkplaces(), 
                                       baseDate: moment(new Date()).toDate(), 
                                       isMultiple: true, 
                                       selectedSystemType: 0, 
                                       isrestrictionOfReferenceRange: false, 
                                       showNoSelection:false, 
                                       isShowBaseDate:false });
            modal("/view/cdl/008/a/index.xhtml").onClosed(function(){
                block.clear();
                let data = getShared('outputCDL008');
                let baseDate = getShared('baseDateCDL008'); 
                if (data && data.length > 0) {
                    self.getAndBindWorkplaceInfo(data, baseDate);
                }
            });
        }

        public getAndBindWorkplaceInfo(workplaceIds, date) {
            let self = this;
        
            let data = {
                workplaceIds: workplaceIds,
                baseDate: moment(date).format('YYYY/MM/DD')
            }
            service.getWorkplaceInfo(data).done((res) => {
                self.registerForm().bindWorkplace(res);
                $('#workplace-list').ntsError('clear');
            });

        }

        /**
        * Export excel
        */
        public exportExcel(): void {
            let self = this;
            block.grayout();
            service.exportExcel().fail(function (error) {
                nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                block.clear();
            }).always(() => {
                block.clear();
            });
        }

        public reCalGridWidth() {
            let panelWidthResize = window.innerWidth - 100;
            let totalColWidth = 550;            
            panelWidthResize = panelWidthResize > totalColWidth ? totalColWidth : panelWidthResize;
            console.log(panelWidthResize);
            $('#workplace-list').igGrid("option", "width", panelWidthResize);
            
            if(panelWidthResize == 550) {
                $('#workplace-list_displayContainer').css("width", panelWidthResize);
                $('#workplace-list_headers_v').css("width", panelWidthResize);
            }
		}

    }
}