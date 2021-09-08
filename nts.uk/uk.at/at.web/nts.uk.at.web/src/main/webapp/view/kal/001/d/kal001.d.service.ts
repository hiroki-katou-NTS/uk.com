module nts.uk.at.view.kal001.d.service {
    import format = nts.uk.text.format;
    import model = nts.uk.at.view.kal001.d.viewmodel;
    var paths = {
        extractAlarm: "at/function/alarm/kal/001/extract/alarm",
        extractResult: "at/function/alarm/kal/001/extract/result/",
        isExtracting: "at/function/alarm/kal/001/isextracting",
        extractStarting: "at/function/alarm/kal/001/extractstarting",
        extractFinished: "at/function/alarm/kal/001/extractfinished"
    };


    export function extractAlarm(taskId: any, numberEmpSuccess: any, statusId: string, listEmployee: Array<model.UnitModel>, alarmCode: string, listPeriodByCategory: Array<model.PeriodByCategory>): JQueryPromise<ExtractedAlarmDto> {
        let command = new ExtractAlarmCommand(listEmployee, alarmCode,
            _.map(listPeriodByCategory, (item) => {
                return new PeriodByCategoryCommand(item);
            }), statusId);

        let def = $.Deferred(), toStopForWriteData = ko.observable(false),
            secondForLoop = listEmployee.length > 50 ? 5000 : 1000;
        let periodCategory = _.find(listPeriodByCategory, function (o) {
            return (o.category == 2 || o.category == 9 || o.category == 4);
        });//スケジュール4週,　複数月次, スケジュール年間
        nts.uk.request.ajax("at", paths.extractAlarm, command).done(function (task: any) {
            taskId(task.id);
            nts.uk.deferred.repeat(conf => conf.task(() => {
                return nts.uk.request.asyncTask.getInfo(task.id, !toStopForWriteData()).done(function (res: any) {
                    let taskData = res.taskDatas;
                    _.forEach(taskData, itemCount => {
                        if (itemCount.key === "empCount") {
                            numberEmpSuccess(itemCount.valueAsNumber);
                        }
                    });
                    let dataWriting: any = _.find(res.taskDatas, function (t) {
                        return t.key === "dataWriting";
                    });
                    if (dataWriting !== null && dataWriting !== undefined && dataWriting.valueAsBoolean === true) {
                        toStopForWriteData(true);
                    }
                    if (res.succeeded) {
                        let data: any = {};
                        let nullData: any = _.find(res.taskDatas, (t) => t.key.indexOf("nullData") >= 0);
                        data["nullData"] = nullData.valueAsBoolean;
                        let eralRecord: any = _.find(res.taskDatas, (t) => t.key.indexOf("eralRecord") >= 0);
                        data["eralRecord"] = eralRecord.valueAsNumber;
                        if (nullData.valueAsBoolean === false) {
                            let dateFormat = ['YYYY/MM', 'YYYY/MM/DD', 'YYYY'];
                            nts.uk.request.ajax("at", paths.extractResult + command.statusProcessId).done(function (result: any) {
                                let empMap = _.groupBy(_.map(result.empInfos, (empData: Array<any>) => {
                                    return {
                                        employeeCode: empData[0],
                                        employeeID: empData[1],
                                        employeeName: empData[2],
                                        workplaceName: empData[3],
                                        start: moment(empData[4], dateFormat),
                                        end: moment(empData[5], dateFormat)
                                    }
                                }), "employeeID");

                                let dataX: Array<any> = [];
                                _.forEach(result.empEralDatas, item => {
                                    let empInfo = null;
                                    if (item[2] !== "") {
                                        let start: any = null, end: any = null;
                                        if (item[2].indexOf("～") > 0) {
                                            let parts = item[2].split("～");
                                            start = moment(parts[0].trim(), dateFormat), end = moment(parts[1].trim(), dateFormat);
                                        } else {
                                            start = moment(item[2], dateFormat);
                                        }
                                        //let eralData = JSON.parse(item.valueAsString);
                                        empInfo = _.find(empMap[item[0]], (e) => {
                                            if (end !== null) {
                                                return e.start.isSameOrBefore(start) && e.end.isSameOrAfter(end);
                                            } else {
                                                return e.start.isSameOrBefore(start) && e.end.isSameOrAfter(start);
                                            }
                                        });
                                    } else {
                                        empInfo = empMap[item[0]][0];
                                    }
                                    if (!_.isNil(empInfo)) {
                                        let alarmDate = item[2];
                                        if (item[8] == 2) { //スケジュール4週
                                            alarmDate = alarmDate + "～" + periodCategory.endDate;
                                        } else if (item[8] == 3 || item[8] == 7) { //スケジュール月次,月次
                                            alarmDate = alarmDate.substr(0, 7);
                                        } else if (item[8] == 9 || item[8] == 4) { //複数月次,スケジュール年間
                                            alarmDate = alarmDate.substr(0, 7) + "～"
                                                + periodCategory.endDate.substr(0, 4) + "/" + periodCategory.endDate.substr(4);
                                        }
                                        dataX.push(_.merge({
                                            guid: item[1],
                                            alarmValueDate: alarmDate,
                                            categoryName: item[3],
                                            alarmItem: item[4],
                                            alarmValueMessage: item[5],
                                            comment: item[6],
                                            checkedValue: item[7]
                                        }, empInfo));
                                    }
                                });
                                data["extractedAlarmData"] = dataX;
                                def.resolve(data);
                            });
                        } else {
                            def.resolve(data);
                        }
                    } else if (res.failed) {
                        def.reject(res.error);
                    }
                });
            }).while(infor => {
                return (infor.pending || infor.running) && infor.status != "REQUESTED_CANCEL";
            }).pause(secondForLoop));
        });

        return def.promise();
    }

    export function isExtracting(): JQueryPromise<boolean> {
        return nts.uk.request.ajax("at", paths.isExtracting);
    }

    export function extractStarting(): JQueryPromise<string> {
        return nts.uk.request.ajax("at", paths.extractStarting);
    }

    export function extractFinished(extraParam: any): JQueryPromise<void> {
        return nts.uk.request.ajax("at", paths.extractFinished, extraParam);
    }

    export interface CheckConditionTimeDto {
        category: number;
        categoryName: string;
        tabOrder: number;
        startDate: string;
        endDate: string;
        startMonth: string;
        endMonth: string;
        year: number;
    }

    export class PeriodByCategoryCommand {
        category: number;
        name: string;
        startDate: string;
        endDate: string;
        period36Agreement: numer;

        constructor(p: model.PeriodByCategory) {

            this.category = p.category;
            this.name = p.categoryName;
            this.period36Agreement = p.period36Agreement;

            if (p.category == 2 || p.category == 5 || p.category == 8 || p.category == 0 || p.category == 6) {//スケジュール4週,日次,申請承認
                this.startDate = nts.uk.time.parseMoment(p.startDate).momentObject.toISOString();
                this.endDate = nts.uk.time.parseMoment(p.endDate).momentObject.toISOString();

            } else if (p.category == 7 || p.category == 9 || p.category == 3 || p.category == 4) { //月次、複数月次
                let sDate = p.startDate + '01';
                let eDate = new Date(p.endDate.substr(0,4), parseInt(p.endDate.substr(4,2)), 0);
                this.startDate = nts.uk.time.parseMoment(sDate).momentObject.toISOString();
                this.endDate = nts.uk.time.parseMoment(eDate).momentObject.toISOString();

            } else if (p.category == 12) {
                this.name = nts.uk.resource.getText("KAL010_208");
                if (p.categoryName == "36協定　1・2・4週間") {
                    this.startDate = nts.uk.time.parseMoment(p.startDate).momentObject.toISOString();
                    this.endDate = nts.uk.time.parseMoment(p.endDate).momentObject.toISOString();

                } else if (p.categoryName == "36協定　年間") {
                    let sMonth = p.startDate.slice(5, 7);
                    let sDate = p.year + '/' + sMonth + "/01";
                    let eDate = "";
                    if (sMonth != 1) {
                        eDate = (parseInt(p.year) + 1) + '/' + p.endDate.slice(5, 7);
                    } else {
                        eDate = p.year + '/' + p.endDate.slice(5, 7);
                    }
                    let lastDay = new Date(Number(eDate.slice(0, 4)), Number(eDate.slice(5, 7)), 0);
                    eDate = eDate + "/" + (lastDay.getDate() < 10 ? "0" + lastDay.getDate() : lastDay.getDate());

                    this.startDate = nts.uk.time.parseMoment(sDate).momentObject.toISOString();
                    this.endDate = nts.uk.time.parseMoment(eDate).momentObject.toISOString();

                } else {
                    let sDate;
                    let eDate = p.endDate;
                    if (p.period36Agreement == 3 || p.period36Agreement == 4 || p.period36Agreement == 5) {
                        sDate = p.startDate + '01';
                    } else {
                        sDate = p.startDate + '/01';
                    }

                    let lastDay = new Date(Number(eDate.slice(0, 4)), Number(eDate.slice(5, 7)), 0);
                    if (p.period36Agreement == 3 || p.period36Agreement == 4 || p.period36Agreement == 5) {
                        eDate = eDate + (lastDay.getDate() < 10 ? "0" + lastDay.getDate() : lastDay.getDate());
                    } else {
                        eDate = eDate + '/' + (lastDay.getDate() < 10 ? "0" + lastDay.getDate() : lastDay.getDate());
                    }

                    this.startDate = nts.uk.time.parseMoment(sDate).momentObject.toISOString();
                    this.endDate = nts.uk.time.parseMoment(eDate).momentObject.toISOString();
                }

            }

        }
    }

    export class ExtractAlarmCommand {
        listEmployee: Array<model.UnitModel>;
        alarmCode: string;
        listPeriodByCategory: Array<PeriodByCategoryCommand>;
        statusProcessId: string;

        constructor(listEmployee: Array<model.UnitModel>, alarmCode: string, listPeriodByCategory: Array<PeriodByCategoryCommand>, statusProcessId: string) {
            this.listEmployee = listEmployee;
            this.alarmCode = alarmCode;
            this.listPeriodByCategory = _.uniqWith(listPeriodByCategory, _.isEqual);
            this.statusProcessId = statusProcessId;
        }
    }

    export interface ValueExtractAlarmDto {
        guid: string;
        workplaceID: string;
        hierarchyCd: string;
        workplaceName: string;
        employeeID: string;
        employeeCode: string;
        employeeName: string;
        alarmValueDate: string;
        category: number;
        categoryName: string;
        alarmItem: string;
        alarmValueMessage: string;
        comment: string;
        checkedValue: string;
    }

    export interface ExtractedAlarmDto {
        extractedAlarmData: Array<ValueExtractAlarmDto>;
        extracting: boolean;
        nullData: boolean;
    }

    export enum AlarmExtraStatus {
        END_NORMAL = 0, /**正常終了*/
        END_ABNORMAL = 1, /**異常終了*/
        PROCESSING = 2, /**処理中*/
        INTERRUPT = 3,    /**中断*/
    }
}
