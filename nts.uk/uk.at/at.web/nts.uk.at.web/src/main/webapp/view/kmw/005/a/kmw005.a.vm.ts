module nts.uk.at.view.kmw005.a {
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import ActualLockFinderDto = service.model.ActualLockFinderDto;
    import ActualLockFindDto = service.model.ActualLockFindDto;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

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
                self.actualLock.closureId.subscribe(function(data: number) {
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
            }

            /**
             * start page data 
             */
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                blockUI.invisible();
                service.findAllActualLock().done(function(data) {
                    blockUI.clear();
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
                    self.actualLockList(dataRes);
                    self.actualLock.closureId(data[0].closureId);
                    dfd.resolve();
                }).fail(error => {
                    blockUI.clear();
                    if (error.messageId == 'Msg_183') {
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_183" });
                    } else {
                        nts.uk.ui.dialog.alertError(error);
                    }
                });
                
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
                    self.addLockIcon();
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
                let self = this;
                let command: any = {};
                command.closureId = self.actualLock.closureId();
                command.dailyLockState = self.actualLock.dailyLockState();
                command.monthlyLockState = self.actualLock.monthlyLockState();
                
                blockUI.invisible();
                service.saveActualLock(command).done(function() {
                    service.saveActualLockHist(command).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                        blockUI.clear();
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res.message).then(() => {blockUI.clear();});
                    });
//                    self.bindActualLock(self.actualLock.closureId());
                    // Reload Page
                    blockUI.invisible();
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
                        self.actualLockList(dataRes);
                        self.addLockIcon();
//                        self.actualLock.closureId(data[0].closureId);
                    })
                    // 
                    blockUI.clear();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message).then(() => {blockUI.clear();});
                });
            }
            

            /**
             * Open Dialog B
             */
            private openDialog(): void {
                let self = this;
                let parrentData = [];
                parrentData = self.actualLockList().map(item => {
                    return item.toClosureDto();
                });
                
                setShared('ClosureList', parrentData, true);

                nts.uk.ui.windows.sub.modal("/view/kmw/005/b/index.xhtml").onClosed(function() {
                    var output = getShared('childData');
//                    self.selectedItem(output);
                });
            }

            /**
             * Add LockIcon
             */
            private addLockIcon() {
                // Add icon to column already setting.
                var iconLink = nts.uk.request.location.siteRoot
                    .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                    .mergeRelativePath('/view/kmw/005/a/images/72.png').serialize();
                $('.icon-72').attr('style', "background: url('" + iconLink + "'); width: 20px; height: 20px; background-size: 20px 20px; margin-left: 27px;")
            }
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
            
            public toClosureDto(): ClosureDto {
                return new ClosureDto (this.closureId, this.closureName);
            }
        }

        export class ClosureDto {
            closureId: number;
            closureName: string;
            
            constructor(closureId: number, closureName: string) {
                this.closureId = closureId;
                this.closureName = closureName;
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