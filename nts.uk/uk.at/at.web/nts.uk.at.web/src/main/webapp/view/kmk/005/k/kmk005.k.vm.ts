module nts.uk.at.view.kmk005.k {
    export module viewmodel {
        import parseT = nts.uk.time.parseTime;
        import unblock = nts.uk.ui.block.clear;
        import block = nts.uk.ui.block.invisible;
        import getText = nts.uk.resource.getText;
        import modal = nts.uk.ui.windows.sub.modal;
        import alert = nts.uk.ui.dialog.alert;
        import alertE = nts.uk.ui.dialog.alertError;
        import setShared = nts.uk.ui.windows.setShared;
        import getShared = nts.uk.ui.windows.getShared;

        let __viewContext: any = window["__viewContext"] || {};

        export class ScreenModel {
            filter: any = {
                startTime: ko.observable('00:00'),
                endTime: ko.observable('00:00'),
                startTimeOption: ko.observable(''),
                endTimeOption: ko.observable(''),
                value: undefined
            };

            model: KnockoutObservable<WorkingTimesheetBonusPaySet> = ko.observable(new WorkingTimesheetBonusPaySet());
            workTimeList: KnockoutObservableArray<WorkTime> = ko.observableArray([]);

            constructor() {
                let self = this,
                    model = self.model();

                $.extend(self.filter, {
                    value: ko.computed(() => {
                        let filter: any = ko.toJS(self.filter),
                            start: any = parseT(filter.startTime, true),
                            vstart: string = start.format() || '00:00',
                            end: any = parseT(filter.endTime, true),
                            vend: string = end.format() || '00:00';

                        return filter.startTimeOption + (vstart.length == 4 ? '0' + vstart : vstart) + ' ~ ' + filter.endTimeOption + (vend.length == 4 ? '0' + vend : vend);
                    })
                });

            }

            loadFirst() {
                let self = this,
                    model = self.model();
                model.wtc.subscribe(v => {
                    let data = self.workTimeList(),
                        row = _.find(data, x => x.code == v);
                    if (row) {
                        model.wtn(row.name);
                        service.getWorkingTimesheetBonusPaySetByCode(v).done(x => {
                            if (x) {
                                model.bpsc(x.bonusPaySettingCode);
                                service.getBonusPaySettingByCode(x.bonusPaySettingCode).done(c => {
                                    if (c) {
                                        model.bpsn(c.name);
                                    } else {
                                        model.bpsc('');
                                        model.bpsn(getText("KDL007_6"));
                                    }
                                });
                            } else {
                                model.bpsc('');
                                model.bpsn(getText("KDL007_6"));
                            }
                        });
                    } else {
                        model.wtc('');
                        model.wtn(getText("KDL007_6"));
                        model.bpsc('');
                        model.bpsn(getText("KDL007_6"));
                    }
                });

                // call filter value computed
                self.filter.startTime.valueHasMutated();

                self.start();
            }
            
            start() {
                let self = this,
                    model = self.model(),
                    workTime = service.getWorkTime(),
                    workPaySet = service.getWorkingTimesheetBonusPaySet();
                block();
                self.workTimeList.removeAll();
                $.when(workTime, workPaySet).done((w: Array<any>, p: Array<any>) => {
                    _.each(w, (item) => {
                        item.flagSet = !!_.find(p, x => item.code == x.workingTimesheetCode);
                        self.workTimeList.push(new WorkTime(item));
                    });
                    model.wtc(w[0].code);
                    model.wtc.valueHasMutated();
                    model.bpsc.valueHasMutated();
                    unblock();
                }).fail(x => alertE({ messageId: x.messageId }).then(unblock)).always(function() {
                    unblock();
                });
            }

            saveData(): void {
                let self = this,
                    model = ko.toJS(self.model),
                    command: any = {
                        action: 1,
                        workingTimesheetCode: model.wtc,
                        bonusPaySettingCode: model.bpsc
                    };
                if (model.bpsc !== '') {
                    if (model.wtc && model.wtc !== '') {
                        block();
                        service.saveSetting(command).done(() => {
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                            self.start();
                            unblock();
                        }).fail((res) => {
                            alertE(res.message).then(function() { unblock(); });
                        }).always(function() {
                    unblock();
                });
                    }
                } else {
                    alert({ messageId: "Msg_30" });
                }
            }

            removeData(): void {
                let self = this,
                    model = ko.toJS(self.model),
                    command: any = {
                        action: 0,
                        workingTimesheetCode: model.wtc,
                        bonusPaySettingCode: model.bpsc
                    };
                if (model.wtc && model.wtc !== '') {
                    block();
                    nts.uk.ui.dialog.confirm({ messageId: "Msg_18" })
                        .ifYes(() => {
                            service.saveSetting(command).done(() => {
                                nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                                self.start();
                                unblock();
                            }).fail(x => alertE(x.message).then(unblock)).always(function() {
                    unblock();
                });
                        
                    }).ifNo(()=>{
                           // model.bpsn(getText("KDL007_6"));                    
                          unblock();
                        });
                }
            }

            search() {
                let self = this,
                    filter: IFilter = ko.toJS(self.filter);

                $('#k_gridlist-tbl>tbody>tr').hide().each((i, x) => {
                    let child = $(x).find('td'),
                        value = child[2] && $(child[2]).text();

                    (value == filter.value) && $(x).show();
                });
            }

            returnData() {
                let self = this,
                    filter = self.filter;

                filter.startTime('00:00');
                filter.endTime('00:00');
                filter.startTimeOption('前日');
                filter.endTimeOption('前日');

                $('#k_gridlist-tbl>tbody>tr').show();
            }

            openDialog() {
                let self = this,
                    model = self.model(),
                    data = ko.toJS(self.model);
                setShared('KDL007_PARAM', { isMulti: false, selecteds: [data.bpsc] });

                modal("../../../kdl/007/a/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(() => {
                    let data = getShared('KDL007_VALUES');
                    if (data && data.selecteds.length) {
                        model.bpsc(data.selecteds[0]);
                        if(!(_.isEmpty(data.selecteds[0]))){
                        service.getBonusPaySettingByCode(data.selecteds[0])
                            .done(x => {
                                if (x) {
                                    model.bpsn(x.name);
                                } else {
                                    model.bpsc('');
                                    model.bpsn(getText("KDL007_6"));
                                }
                            })
                            .fail(x => {
                                model.bpsc('');
                                model.bpsn(getText("KDL007_6"));
                            });
                        }else{
                            model.bpsc('');
                            model.bpsn(getText("KDL007_6"));
                        }
                    }
                });
            }
        }

        // interface for filter
        interface IFilter {
            startTime: string;
            endTime: string;
            startTimeOption: number;
            endTimeOption: number;
            value: string;
        }

        interface IWorkTime {
            code: string;
            name: string;
            workTime1: string;
            workTime2: string;
            flagSet?: boolean;
        }

        class WorkTime {
            code: string;
            name: string;
            workTime1: string;
            workTime2: string;
            flagSet: boolean;
            flagSet2: string;

            constructor(param: IWorkTime) {
                let self = this;

                self.code = param.code;
                self.name = param.name;
                self.workTime1 = param.workTime1;
                self.workTime2 = param.workTime2;
                self.flagSet = param.flagSet || false;
                self.flagSet2 = param.flagSet ? '<span style="font-size: 1.2em;margin-left: 35%;color: #63C28A;">✔</span' : '';
            }
        }

        class WorkingTimesheetBonusPaySet {
            // working time sheet code
            wtc: KnockoutObservable<string> = ko.observable('');
            // working time sheet name
            wtn: KnockoutObservable<string> = ko.observable('');
            // bonus pay setting code
            bpsc: KnockoutObservable<string> = ko.observable('');
            // bonus pay setting name
            bpsn: KnockoutObservable<string> = ko.observable('');

            constructor() {
                var self = this;
                self.bpsc.subscribe(x => {
                    let view = __viewContext.viewModel && __viewContext.viewModel.tabView,
                        acts: any = view && _.find(view.tabs(), (t: any) => t.active());
                    if (acts && acts.id == 'K') {
                        view.removeAble(!!x);
                    }
                });
            }
        }
    }
}