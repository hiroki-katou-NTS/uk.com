module nts.uk.at.view.kmw005.a {
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import ClosureHistoryMDto = service.model.ClosureHistoryMasterDto;
    import ClosureHistoryDDto = service.model.ClosureHistoryHeaderDto;
    import ActualLockFinderDto = service.model.ActualLockFinderDto;
    import ActualLockFindDto = service.model.ActualLockFindDto;

    export module viewmodel {

        export class ScreenModel {
            actualLockList: KnockoutObservableArray<ActualLockFind>;
            selectedClosure: KnockoutObservable<number>;
            actualLockColumn: KnockoutObservableArray<any>;
            actualLock: ActualLock;
            dailyActualLockOpt: KnockoutObservableArray<any>;
            monthlyActualLockOpt: KnockoutObservableArray<any>;
            selectedClosureText: KnockoutObservable<string>;
            closureName: KnockoutObservable<string>;


            constructor() {
                var self = this;
                self.actualLock = new ActualLock();
                self.actualLockList = ko.observableArray<ActualLockFind>([]);
                self.selectedClosure = ko.observable(1);
                self.selectedClosure.subscribe(function(data: number) {
                    self.bindActualLock(data);
                });
                self.actualLockColumn = ko.observableArray([
                    { headerText: getText(''), key: 'closureId', hide: true },
                    { headerText: getText('KMW005_3'), key: 'closureName', width: 100 },
                    { headerText: getText('KMW005_4'), key: 'period', width: 180 },
                    { headerText: getText('KMW005_5'), key: 'dailyLockState', width: 90, formatter: lockIcon },
                    { headerText: getText('KMW005_6'), key: 'monthlyLockState', width: 90, formatter: lockIcon }
                ]);

                self.dailyActualLockOpt = ko.observableArray([
                    { code: '1', name: getText("KMW005_11") },
                    { code: '0', name: getText("KMW005_12") }
                ]);

                self.monthlyActualLockOpt = ko.observableArray([
                    { code: '1', name: getText("KMW005_15") },
                    { code: '0', name: getText("KMW005_16") }
                ]);
                self.closureName = ko.observable("");
                self.selectedClosureText = ko.computed(function() {
                    return nts.uk.resource.getText("KMW005_7", [self.closureName()]);
                });
                self.actualLock.dailyLockState.subscribe(function() {
                    self.addLockIcon();
                });

            }

            /**
             * start page data 
             */
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.findAllActualLock().done(function(data) {
                    var dataRes: ActualLockFind[] = [];
                    for (var item: ActualLockFinderDto of data) {
                        var actualLock: ActualLockFind = new ActualLockFind();
                        actualLock.closureId = item.closureId;
                        actualLock.closureName = item.closureName;
                        actualLock.dailyLockState = item.dailyLockState;
                        actualLock.monthlyLockState = item.monthlyLockState;
                        actualLock.startDate = item.startDate;
                        actualLock.endDate = item.endDate;
                        actualLock.period = item.startDate + " ~ " + item.endDate;
                        dataRes.push(actualLock);
                    }
                    // Sort by closureId
                    dataRes.sort(function(left, right) {
                        return left.closureId == right.closureId ?
                            0 : (left.closureId < right.closureId ? -1 : 1)
                    });
                    self.actualLockList(dataRes);
                    self.addLockIcon();
                    self.selectedClosure(data[0].closureId);
                }).fail(error => {
                    if (error.messageId == 'Msg_183') {
                        nts.uk.ui.dialog.info({ messageId: "Msg_183" });
                    } else {
                        nts.uk.ui.dialog.alertError(error);
                    }
                    blockUI.clear();
                });
                dfd.resolve();
                return dfd.promise();
            }

            /**
             * Bind ActualLock
             */
            private bindActualLock(closureId: number): void {
                let self = this;
                service.findLockByClosureId(closureId).done(function(data: ActualLockFindDto) {
                    self.actualLock.updateLock(data);
                    // ClosureName
                    var currentClosure = self.actualLockList().filter((item) => {
                        return item.closureId == closureId;
                    })[0];
                    self.closureName(currentClosure.closureName);
                });
            }


            /**
             * collectActualLockData 
             */
            private collectActualLockData(): void {
                let self = this;
                let command: any = {};
                command.closureId = self.actualLock.closureId();
                command.dailyLockState = self.actualLock.dailyLockState();
                command.monthlyLockState = self.actualLock.monthlyLockState();
            }
            
            /**
             * Save ActualLock
             */
            private saveActualLock(): void {
                let selt = this;
                let command: any = {};
                command.closureId = self.actualLock.closureId();
                command.dailyLockState = self.actualLock.dailyLockState();
                command.monthlyLockState = self.actualLock.monthlyLockState();
                
                blockUI.invisible();
//                service.saveActualLock(command).done(function() {
//                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
//                        
//                    });
//                    blockUI.clear();
//                }).fail(error => {
////                    if (error.messageId == 'Msg_3') {
////                        nts.uk.ui.dialog.info({ messageId: "Msg_3" }).then(function() {
////                            $("#empCode").focus();
////                        });
////                    } else {
////                        nts.uk.ui.dialog.alertError(error);
////                    }
////                    blockUI.clear();
//                });
            }

            /**
             * Open Dialog B
             */
            private openDialog(): void {
                let self = this;
            }

            /**
             * Add LockIcon
             */
            private addLockIcon() {
                // Add icon to column already setting.
                var iconLink = nts.uk.request.location.siteRoot
                    .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                    .mergeRelativePath('/view/kmw/005/a/images/72.png').serialize();
                $('.icon-72').attr('style', "background: url('" + iconLink + "'); width: 20px; height: 20px; background-size: 20px 20px;")
            }

            /**
             * validate client by action click
             */
            //            validateClient(): boolean {
            //                var self = this;
            //                self.clearValiate();
            //                if (self.actualLock.useClassification() == 1) {
            //                    $("#inpMonth").ntsEditor("validate");
            //                    $("#inpname").ntsEditor("validate");
            //                    if ($('.nts-input').ntsError('hasError')) {
            //                        return true;
//                    }
            //                }
            //                return false;
            //            }
            
            /**
             * save closure history by call service
             */
            //            saveClosureHistory(): void {
            //                var self = this;
            //                if (self.actualLock.useClassification() == 1 && self.validateClient()) {
            //                    return;
            //                }
            //                self.clearValiate();
            //                service.saveClosure(self.collectData()).done(function() {
            //                    if (self.actualLock.useClassification() == 1) {
            //                        service.saveClosureHistory(self.collectDataHistory()).done(function() {
            //                            nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
            //                                self.reloadPage(self.selectCodeLstClosure().id,
            //                                    self.selectCodeLstClosureHistory().startDate);
            //                            });
            //                        }).fail(function(error) {
            //                            nts.uk.ui.dialog.alertError(error).then(function() {
            //                                self.reloadPage(self.selectCodeLstClosure().id,
            //                                    self.selectCodeLstClosureHistory().startDate);
            //                            });
            //                        });
            //                    } else {
            //                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
            //                            self.reloadPage(self.selectCodeLstClosure().id,
            //                                self.selectCodeLstClosureartDate);
            //                        });
            //                    }
            //                }).fail(function(error) {
            //                    nts.uk.ui.dialog.alertError(error).then(function() {
            //                        self.reloadPage(self.selectCodeLstClosure().id,
            //                            self.selectCodeLstClosureHistory().startDate);
            //                    });
            //                });
            //            }
            
            

            /**
             * reload page 
             */
            //            reloadPage(closureId: number, startDate: number): void{
            //                var self = this;
            //                 service.findAllClosureHistory().done(function(data) {
            //                    var dataRes: ClosureHistoryFindDto[] = [];
            //                    for (var item: ClosureHistoryFindDto of data) {
            //                        var dataI: ClosureHistoryFindDto = new ClosureHistoryFindDto();
            //                        dataI.id = item.id; 
            //                        dataI.name = item.name;
            //                        dataI.updateData();
            //                        dataRes.push(dataI);
            //                    }
            //                   self.actualLockList(dataRes);
            //                   for (var closure: ClosureHistoryFindDto of data){
            //                        if(closure.id == closureId){
            //                            self.selectCodeLstClosure(closure);
            //                            selure(closureId);
            //                            return;    
            //                        }    
            //                   }
            //                   
            //                });
            //            }
            
            /**
             * collect data closure history 
             */
            //            collectDataHistory(): ClosureHistoryDto{
            //                var self = this;
            //                var dto: ClosureHistoryDto;
            //                dto = new ClosureHistoryDto();
            //                dto.clos.closureHistoryModel.closureName();
            //                dto.closureId = self.closureHistoryModel.closureId();
            //                dto.closureDate = self.closureHistoryModel.closureDate();
            //                dto.startDate = self.closureHistoryModel.startDate();
            //                return dto;    
            //            }
            
            // 締め期間確認 
            /**
             * open dialog D
             */
            //            public openConfirmClosingPeriodDialog(): void {
            //                var self = this;
            //                nts.uk.ui.windows.setShared('closureId', self.actualLock.closureId());
//              nts.uk.ui.windows.setShared('startDate', self.closureHistoryModel.startDate());
            //                nts.uk.ui.windows.sub.modeless('/view/kmk/012/d/index.xhtml', 
            //                { title: '締め期間確認 ', dialogClass: 'no-close' }).onClosed(function(){
            //                    self.reloadPage(self.actualLock.closureId(), self.closureHistoryModel.startDate());    
            //                });
            //            }
    
            
        }

        function lockIcon(value, row) {
            if (value == '1')
                return "<i class='icon icon-72'></i>";
            return '';
        }

        //        function lockIcon1 (isLock: string) {
        //            if (isLock == '1') {
        //                return '<div style="text-align: center;max-height: 18px;"><i class="icon icon-72"></i></div>';
        //            }
        //         return '';
        //        }
        
        

        export class ActualLockFind {
            /** The closure id. */
            closureId: number;
            /** dailyLockState. */
            dailyLockState: number;

            monthlyLockState: number;

            closureName: string;

            startDate: string;

            endDate: string;

            period: string;

            constructor() {
                this.closureId = 0;
                this.dailyLockState = 0;
                this.monthlyLockState = 0;
                this.closureName = '';
                this.startDate = '';
                this.endDate = '';
                this.period = '';
            }
        }

        export class ActualLock {
            closureId: KnockoutObservable<number>;
            dailyLockState: KnockoutObservable<number>;
            monthlyLockState: KnockoutObservable<number>;

            constructor() {
                this.closureId = ko.observable(0);
                this.dailyLockState = ko.observable(0);
                this.monthlyLockState = ko.observable(0);
            }

            updateLock(dto: ActualLockFindDto): void {
                this.closureId(dto.closureId);
                this.dailyLockState(dto.dailyLockState);
                this.monthlyLockState(dto.monthlyLockState);
            }
        }

    }
}