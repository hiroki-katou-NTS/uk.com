module nts.uk.at.view.ksu001.ab.viewmodel {
    import setShare = nts.uk.ui.windows.setShared;
    import getShare = nts.uk.ui.windows.getShared;
    import formatById = nts.uk.time.format.byId;
    import alertError = nts.uk.ui.dialog.alertError;
    import getText = nts.uk.resource.getText;

    export class ScreenModel {
                
        listWorkType: KnockoutObservableArray<ksu001.a.viewmodel.IWorkTypeDto> = ko.observableArray([]);
        selectedWorkTypeCode: KnockoutObservable<string>;
        input : any;
        
        constructor() {
            let self = this;
            
            self.selectedWorkTypeCode = ko.observable('');
            self.selectedWorkTypeCode.subscribe((newValue) => {
                console.log(newValue);
            });

            self.input = {
                fillter: false,
                workPlaceId: 'abc',
                initiallySelected: ['002'],
                displayFormat: '',
                showNone: false,
                showDeferred: false,
                selectMultiple: true
            }
        }

        /**
         * search workTime
         */
        search(): void {
            let self = this;
            let listWorkTimeSearch: any[] = [];
            let arrTmp: any[] = [];
            self.isEnableClearSearchButton(true);
            if (self.time1() === '' && self.time2() === '') {
                alertError({ messageId: "Msg_53" });
                self.isEnableClearSearchButton(false);
                self.clear();
                return;
            }
            if (!((self.time1() == '' && self.time2() !== '') || (self.time1() !== '' && self.time2() == '')) && self.time1() > self.time2()) {
                alertError({ messageId: "Msg_54" });
                self.clear();
                return;
            }
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            self.listWorkTimeComboBox([]);
            
            if(self.time2() === ''){
               listWorkTimeSearch = _.filter(self.listTimeZoneForSearch, {'startTime' : self.time1(), 'useAtr' : 1});
            } else if(self.time1() === ''){
                listWorkTimeSearch = _.filter(self.listTimeZoneForSearch, {'endTime' : self.time2(), 'useAtr' : 1});
            } else {
                listWorkTimeSearch = _.filter(self.listTimeZoneForSearch, { 'startTime': self.time1(), 'endTime': self.time2(), 'useAtr': 1});
            }
            
            if (listWorkTimeSearch.length <= 0) {
                return;
            }
            
            _.each(listWorkTimeSearch, (x) => {
                arrTmp.push(_.find(self.listWorkTime(), { 'workTimeCode': x.workTimeCode }));
            });
            
            self.listWorkTimeComboBox(arrTmp);
            
            $('#combo-box2').focus();
        }

        /**
         * clear search time
         */
        clear(): void {
            let self = this;
            self.isEnableClearSearchButton(false);
            self.listWorkTimeComboBox([]);
            self.listWorkTimeComboBox(self.listWorkTime());
            self.time1('');
            self.time2('');
            nts.uk.ui.errors.clearAll();
        }

        /**
         * get data workType-workTime for 2 combo-box and startDate-endDate
         * get startDate, endDate give to A1_1(CCG001) 
         * get startDate, endDate for screen A
         * checkNeededOfWorkTimeSetting(): get list state of workTypeCode relate to need of workTime
         */
        initScreen(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            service.initScreen().done(function(data) {
                self.employeeIdLogin = data.employeeIdLogin;
                //set date for startDate and endDate
                self.startDateScreenA = data.startDate;
                self.endDateScreenA = data.endDate;
                //set data for listWorkType
                self.listWorkType(data.listWorkType);
                self.checkStateWorkTypeCode = data.checkStateWorkTypeCode;
                self.checkNeededOfWorkTimeSetting = data.checkNeededOfWorkTimeSetting;
                self.workEmpCombines = data.workEmpCombines;
                self.selectedWorkTypeCode(self.listWorkType()[0].workTypeCode);
                self.listTimeZoneForSearch = data.listWorkTime;
            
                self.listWorkTime.push(new ksu001.common.modelgrid.WorkTime({
                    workTimeCode: '',
                    name: self.nashi,
                    abName: '',
                    symbolName: '',
                    dailyWorkAtr: undefined,
                    worktimeSetMethod: undefined,
                    abolitionAtr: undefined,
                    color: null,
                    note: null,
                    startTime: undefined,
                    endTime: undefined,
                    workNo: undefined,
                    useAtr: undefined
                }));
    
                _.each(data.listWorkTime, function(wT) {
                    let workTimeObj: ksu001.common.modelgrid.viewmodel.WorkTime = _.find(self.listWorkTime(), ['workTimeCode', wT.workTimeCode]);
                    if (workTimeObj && wT.workNo == 1) {
                        workTimeObj.timeZone1 = formatById("Clock_Short_HM", wT.startTime) + getText("KSU001_66")
                            + formatById("Clock_Short_HM", wT.endTime);
                    } else if (workTimeObj && wT.workNo == 2) {
                        workTimeObj.timeZone2 = wT.useAtr == 1 ? (formatById("Clock_Short_HM", wT.startTime)
                            + getText("KSU001_66") + formatById("Clock_Short_HM", wT.endTime)) : '';
                    } else {
                        self.listWorkTime.push(new ksu001.common.modelgrid.WorkTime({
                            workTimeCode: wT.workTimeCode,
                            name: wT.name,
                            abName: wT.abName,
                            symbolName: wT.symbol,
                            dailyWorkAtr: wT.dailyWorkAtr,
                            worktimeSetMethod: wT.worktimeSetMethod,
                            abolitionAtr: wT.abolitionAtr,
                            color: wT.color,
                            note: wT.note,
                            startTime: wT.startTime,
                            endTime: wT.endTime,
                            workNo: wT.workNo,
                            useAtr: wT.useAtr
                        }));
                    }
                });
                dfd.resolve();
                self.listWorkTimeComboBox(self.listWorkTime());
                self.selectedWorkTimeCode(self.listWorkTimeComboBox()[0].codeName);
            }).fail(function() {
                dfd.reject();
            });
            return dfd.promise();
        }
    }
}