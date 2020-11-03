module nts.uk.at.view.kcp006.a {
    /**
     * Calendar binding handler
     */
    // pass chosen date to delegate click cell function
    var defaultOption = {
        buttonClick: function(date) { },
        cellClick: function(date) { }
    };
    $.fn.ntsCalendar = function(action: string, option: any) {
        if (action == "init") {
            var $control = $(this);
            var setting = $.extend({}, defaultOption, option);
            $control.off("click", ".button-cell");
            $control.on("click", ".button-cell", function(event) {
                event.preventDefault();
                event.stopPropagation();
                var choosenDate = $(this).attr("data-date");
                setting.buttonClick.call(this, choosenDate);
            });
            $control.off("click", "td .fc-day");
            $control.on("click", "td .fc-day", function(event) {
                event.preventDefault();
                event.stopPropagation();
                var choosenDate = $(this).attr("data-date");
                if (choosenDate) {
                    setting.cellClick.call(this, choosenDate);
                }
            });
            return $control;
        }
    }

    var _lstHoliday = [];
    var _lstEvent = [];
    var _lstDate = [];

    class CalendarBindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            let service = this.nts.uk.at.view.kcp006.a.service;
            //property default
            let eventDisplay = true;
            let eventUpdatable = true;
            let holidayDisplay = true;
            let cellButtonDisplay = true;
            let firstDay = 0;
            let workplaceId = "0";
            let workplaceName = "";
            let yearMonth = moment().format("YYYYMM");
            let startDate = moment(yearMonth, "YYYYMM").startOf('month').date();
            let endDate = moment(yearMonth, "YYYYMM").endOf('month').date();
			let hidden = false;
            //get params
            let data = valueAccessor();
            let optionDates = ko.unwrap(data.optionDates());
            if (data.yearMonth()) { yearMonth = ko.unwrap(data.yearMonth()) };
            eventDisplay = ko.unwrap(data.eventDisplay());
            eventUpdatable = ko.unwrap(data.eventUpdatable());
            holidayDisplay = ko.unwrap(data.holidayDisplay());
            cellButtonDisplay = ko.unwrap(data.cellButtonDisplay());
            if (data.startDate) {
                if (moment(yearMonth * 100 + data.startDate, "YYYYMMDD")._isValid) {
                    startDate = data.startDate;
                } else {
                    startDate = moment(yearMonth, "YYYYMM").endOf('month').date();
                }
            };
            if (data.endDate) {
                if (moment(yearMonth * 100 + data.endDate, "YYYYMMDD")._isValid) {
                    endDate = data.endDate;
                } else {
                    endDate = moment(yearMonth, "YYYYMM").endOf('month').date();
                }
            };
            //get list date
            let lstDate = [];
            if (startDate < endDate) {
                for (let i = startDate; i <= endDate; i++) {
                    lstDate.push(moment(yearMonth * 100 + i, "YYYYMMDD").format("YYYYMMDD"));
                }
            } else {
                for (let i = startDate; i <= 31; i++) {
                    lstDate.push(moment(yearMonth * 100 + i, "YYYYMMDD").format("YYYYMMDD"));
                }
                for (let i = 1; i <= endDate; i++) {
                    lstDate.push(moment((yearMonth + 1) * 100 + i, "YYYYMMDD").format("YYYYMMDD"));
                }
            }
            _.remove(lstDate, (val) => {
                return val === "Invalid date";
            });
            if (data.workplaceId()) { workplaceId = ko.unwrap(data.workplaceId()); }
            if (data.workplaceName()) { workplaceName = ko.unwrap(data.workplaceName()); }
			if (data.hidden) {
				hidden = data.hidden;
			}
            // Container
            let container = $(element);
            //set width
            container.css("width", "700px");
            // create duration month
            let durationMonth = 1;
            if (startDate >= endDate) {
                durationMonth = 2;
            };
            $(container).fullCalendar({
                header: false,
                defaultView: 'customMonth',
                views: {
                    customMonth: {
                        type: 'month',
                        duration: { months: 3 }
                    }
                },
                dateAlignment: "week",
                eventLimitText: function(countMore) {
                    return '。。。';
                },
                eventOrder: 'id',
                defaultDate: moment(yearMonth * 100 + startDate, "YYYYMMDD").format("YYYY-MM-DD"),
                height: 550,
                showNonCurrentDates: false,
                handleWindowResize: false,
                dragable: false,
                locale: "ja",
                navLinks: false, // can't click day/week names to navigate views
                editable: false,
                eventLimit: true // allow "more" link when too many events
            });
            //render view after load db
            let fullCalendarRender = new nts.uk.at.view.kcp006.a.FullCalendarRender();
			if(!hidden) {
            fullCalendarRender.loadDataFromDB(lstDate, _lstHoliday, _lstEvent, workplaceId).done(() => {
                $(container).fullCalendar('option', {
                    firstDay: firstDay,
                    validRange: fullCalendarRender.validRange(yearMonth, startDate, endDate, durationMonth),
                    viewRender: function(view, element) {
                        fullCalendarRender.viewRender(container[0].id, optionDates, firstDay, _lstHoliday, _lstEvent, eventDisplay, holidayDisplay, cellButtonDisplay, workplaceId);
                    },
                    eventAfterAllRender: function(view) {
                        fullCalendarRender.eventAfterAllRender(container[0].id, lstDate, _lstHoliday, _lstEvent, workplaceId, workplaceName, eventUpdatable, optionDates);
                    }
                });
                $(container).fullCalendar('gotoDate', moment(yearMonth * 100 + startDate, "YYYYMMDD").format("YYYY-MM-DD"));
            });
			}	
            _lstDate = lstDate;
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            //property default
            let eventDisplay = true;
            let eventUpdatable = true;
            let holidayDisplay = true;
            let cellButtonDisplay = true;
            let firstDay = 0;
            let workplaceId = "0";
            let workplaceName = "";
            let yearMonth = moment().format("YYYYMM");
            let startDate = moment(yearMonth, "YYYYMM").startOf('month').date();
            let endDate = moment(yearMonth, "YYYYMM").endOf('month').date();
			let reloadOnSameYM = false;
			let hidden = false;
            //get params
            let data = valueAccessor();
            let optionDates = ko.unwrap(data.optionDates());
            if (data.yearMonth()) { yearMonth = ko.unwrap(data.yearMonth()) };
            eventDisplay = ko.unwrap(data.eventDisplay());
            eventUpdatable = ko.unwrap(data.eventUpdatable());
            holidayDisplay = ko.unwrap(data.holidayDisplay());
            cellButtonDisplay = ko.unwrap(data.cellButtonDisplay());
            if (data.firstDay) { firstDay = ko.unwrap(data.firstDay()) };
            if (data.startDate) {
                if (moment(yearMonth * 100 + data.startDate, "YYYYMMDD")._isValid) {
                    startDate = data.startDate;
                } else {
                    startDate = moment(yearMonth, "YYYYMM").endOf('month').date();
                }
            };
            if (data.endDate) {
                if (moment(yearMonth * 100 + data.endDate, "YYYYMMDD")._isValid) {
                    endDate = data.endDate;
                } else {
                    endDate = moment(yearMonth, "YYYYMM").endOf('month').date();
                }
            };
            if (data.workplaceId()) { workplaceId = ko.unwrap(data.workplaceId()); }
            if (data.workplaceName()) { workplaceName = ko.unwrap(data.workplaceName()); }
			if (data.reloadOnSameYM) {
				reloadOnSameYM = data.reloadOnSameYM;	
			}
			if (data.hidden) {
				hidden = data.hidden;
			}
            // Container
            let container = $(element);
            //set width
            container.css("width", "600px");
            //get list date
            let lstDate = [];
            if (startDate < endDate) {
                for (let i = startDate; i <= endDate; i++) {
                    lstDate.push(moment(yearMonth * 100 + i, "YYYYMMDD").format("YYYYMMDD"));
                }
            } else {
                for (let i = startDate; i <= 31; i++) {
                    lstDate.push(moment(yearMonth * 100 + i, "YYYYMMDD").format("YYYYMMDD"));
                }
                for (let i = 1; i <= endDate; i++) {
                    lstDate.push(moment((yearMonth + 1) * 100 + i, "YYYYMMDD").format("YYYYMMDD"));
                }
            }
            _.remove(lstDate, (val) => {
                return val === "Invalid date";
            });
            // create duration month
            let durationMonth = 1;
            if (startDate >= endDate) {
                durationMonth = 2;
            };
            //render view after load db
            let fullCalendarRender = new nts.uk.at.view.kcp006.a.FullCalendarRender();
			if(!hidden) {
            if (_.difference(lstDate, _lstDate).length > 0 || reloadOnSameYM) {
				_lstHoliday = [];
                fullCalendarRender.loadDataFromDB(lstDate, _lstHoliday, _lstEvent, workplaceId).done(() => {
                    $(container).fullCalendar('option', {
                        firstDay: firstDay,
                        validRange: fullCalendarRender.validRange(yearMonth, startDate, endDate, durationMonth),
                        viewRender: function(view, element) {
                            fullCalendarRender.viewRender(container[0].id, optionDates, firstDay, _lstHoliday, _lstEvent, eventDisplay, holidayDisplay, cellButtonDisplay, workplaceId);
                        },
                        eventAfterAllRender: function(view) {
                            fullCalendarRender.eventAfterAllRender(container[0].id, lstDate, _lstHoliday, _lstEvent, workplaceId, workplaceName, eventUpdatable, optionDates);
                        }
                    });
                    $(container).fullCalendar('gotoDate', moment(yearMonth * 100 + startDate, "YYYYMMDD").format("YYYY-MM-DD"));
                });
            } else if (optionDates.length > 0) {
                //                                service.getPublicHoliday(lstDate).done((data: Array<model.EventObj>) => {
                //                //                    _lstHoliday = [];
                //                //                    data.forEach((a) => { _lstHoliday.push({ start: moment(a.date).format("YYYY-MM-DD"), holidayName: a.holidayName }); });
                //               
                //                
                //                               });
                if (fullCalendarRender.insertText(container[0].id, optionDates)) {
                    return;
                }
                setTimeout(function() {

                    $(container).fullCalendar('option', {
                        viewRender: function(view, element) {
                            fullCalendarRender.viewRender(container[0].id, optionDates, firstDay, _lstHoliday, _lstEvent, eventDisplay, holidayDisplay, cellButtonDisplay, workplaceId);
                        }
                    });
                    $(container).fullCalendar('option', {
                        eventAfterAllRender: function(view) {
                            fullCalendarRender.eventAfterAllRender(container[0].id, lstDate, _lstHoliday, _lstEvent, workplaceId, workplaceName, eventUpdatable, optionDates);
                        }
                    });
                }, 10);

            }
			}
            _lstDate = lstDate;
        }
    }

    ko.bindingHandlers['ntsCalendar'] = new CalendarBindingHandler();

    export class FullCalendarRender {

        constructor() {

        }

        validRange(yearMonth, startDate, endDate, durationMonth) {
            let start = moment(yearMonth * 100 + startDate, "YYYYMMDD");
            let end = moment(yearMonth * 100 + endDate + 1, "YYYYMMDD");
            if (durationMonth == 2) {
                end = moment(yearMonth * 100 + endDate, "YYYYMMDD").add(1, 'M').add(1, 'day');
            }
            if (end._isValid) {
                return {
                    start: start.format("YYYY-MM-DD"),
                    end: end.format("YYYY-MM-DD")
                };
            } else {
                if (durationMonth == 1) {
                    return {
                        start: start.format("YYYY-MM-DD"),
                        end: moment(yearMonth, "YYYYMM").add(1, 'M').startOf('month').format("YYYY-MM-DD")
                    };
                } else {
                    return {
                        start: start.format("YYYY-MM-DD"),
                        end: moment(yearMonth, "YYYYMM").add(1, 'M').endOf('month').add(1, 'day').format("YYYY-MM-DD")
                    };
                }
            }
        }

        loadDataFromDB(lstDate, lstHoliday, lstEvent, workplaceId): JQueryPromise<any> {
            let dfdLoadDB = $.Deferred<any>();
            // list holiday received from server
            let dfdGetHoliday = $.Deferred<any>();
            service.getPublicHoliday(lstDate)
                .done((data: Array<model.EventObj>) => {
                    data.forEach((a) => { lstHoliday.push({ start: moment(a.date).format("YYYY-MM-DD"), holidayName: a.holidayName }); });
                    dfdGetHoliday.resolve();
                });
            // list event received from server
            let dfdGetEvent = $.Deferred<any>();
            let dfdGetCompanyEvent = $.Deferred<any>();
            let dfdGetWorkplaceEvent = $.Deferred<any>();
            let lstResultData = [];
            service.getCompanyEvent(lstDate)
                .done((data: Array<model.EventObj>) => {
                    if (data) {
                        data.forEach((a) => { lstResultData.push({ start: moment(a.date).format("YYYY-MM-DD"), companyEvent: a.name }); });
                    }
                    dfdGetCompanyEvent.resolve();
                });
            service.getWorkplaceEvent({ workplaceId: workplaceId, lstDate: lstDate })
                .done((data: Array<model.EventObj>) => {
                    if (data) {
                        data.forEach((a) => { lstResultData.push({ start: moment(a.date).format("YYYY-MM-DD"), workplaceEvent: a.name }); });
                    }
                    dfdGetWorkplaceEvent.resolve();
                });
            $.when(dfdGetCompanyEvent, dfdGetWorkplaceEvent)
                .done(() => {
					lstEvent.forEach(function(value: any, i: number) {
                        var existing = lstResultData.filter(function(v) {
                            return v.start == value.start;
                        });
						if (!existing.length) {
							lstEvent.splice(i, 1);
						}
					});
                    lstResultData.forEach(function(value) {
                        var existing = lstEvent.filter(function(v, i) {
                            return v.start == value.start;
                        });
                        if (existing.length) {
                            var existingIndex = lstEvent.indexOf(existing[0]);
                            if (lstEvent[existingIndex].companyEvent) {
                                lstEvent[existingIndex].workplaceEvent = value.workplaceEvent;
                            } else {
                                lstEvent[existingIndex].companyEvent = value.companyEvent;
                            }
                        } else {
                            lstEvent.push(value);
                        }
                    });
                    dfdGetEvent.resolve();
                });
            $.when(dfdGetEvent, dfdGetHoliday)
                .done(() => {
                    dfdLoadDB.resolve();
                })
                .fail(() => {
                    dfdLoadDB.reject();
                });
            return dfdLoadDB.promise();
        }

        fillHolidayAndEventData(currentCalendar, lstHoliday, lstEvent, workplaceId): void {
            //blanking value
            $("#" + currentCalendar + " .holiday").find("span").html("");
            $("#" + currentCalendar + " .holiday-header").removeClass("holiday-header");
            $("#" + currentCalendar + " .holiday-name").removeClass("holiday-header");
            $("#" + currentCalendar + " .button-event").attr("src", "/nts.uk.at.web/view/kcp/006/a/fullcalendar-lib/icon/121.png");
            $("#" + currentCalendar + " .event-data").empty();
            //fill data
            for (let i = 0; i < lstHoliday.length; i++) {
                //update css
                $("#" + currentCalendar + " .fc-day-top[data-date='" + lstHoliday[i].start + "']").addClass("holiday-header");
                $("#" + currentCalendar + " .holiday td[data-date='" + lstHoliday[i].start + "']").addClass("holiday-header holiday-name");
                //fill holiday name
                $("#" + currentCalendar + " .holiday td[data-date='" + lstHoliday[i].start + "']").find("span").html(lstHoliday[i].holidayName);
            }
            for (let i = 0; i < lstEvent.length; i++) {
                $("#" + currentCalendar + " .event-data[data-date='" + lstEvent[i].start + "']").append("<span class='com-event-label'>" + nts.uk.resource.getText("KCP006_3") + ":</span><br/>");
                if (lstEvent[i].companyEvent) {
                    $("#" + currentCalendar + " .event-data[data-date='" + lstEvent[i].start + "']").append("<span class='com-event-content'>" + lstEvent[i].companyEvent + "</span><br/>");
                } else {
                    $("#" + currentCalendar + " .event-data[data-date='" + lstEvent[i].start + "']").append("<span class='com-event-content'></span><br/>");
                }
                if (workplaceId && workplaceId !== "" && workplaceId !== "0") {
                    $("#" + currentCalendar + " .event-data[data-date='" + lstEvent[i].start + "']").append("<span class='wkp-event-label'>" + nts.uk.resource.getText("KCP006_4") + ":</span><br/>");
                    if (lstEvent[i].workplaceEvent) {
                        $("#" + currentCalendar + " .event-data[data-date='" + lstEvent[i].start + "']").append("<span class='wkp-event-content'>" + lstEvent[i].workplaceEvent + "</span>");
                    } else {
                        $("#" + currentCalendar + " .event-data[data-date='" + lstEvent[i].start + "']").append("<span class='wkp-event-content'></span>");
                    }
                }
                //change icon button
                $("#" + currentCalendar + " .button-event[data-date='" + lstEvent[i].start + "']").attr("src", "/nts.uk.at.web/view/kcp/006/a/fullcalendar-lib/icon/120.png");
            }
        }
        insertText(currentCalendar, optionDates): boolean{
            let result: boolean = false;
            for (let i = 0; i < optionDates.length; i++) {
                if(optionDates[i].insertText != true)
                    continue;
                let displayText = "";
                for (let j = 0; j < optionDates[i].listText.length; j++) {
                    if (optionDates[i].listText.length <= 3) {
                        displayText += optionDates[i].listText[j] + '<br/>';
                    } else {
                        if (j < 2) {
                            displayText += optionDates[i].listText[j] + '<br/>';
                        } else if (j == 2) {
                            displayText += "。。。";
                        }
                    }
                }
                displayText = displayText.replace(new RegExp('\r?\n', 'g'), '<br/>');
                let $elementWrap = $(_.find($($("#" + currentCalendar + " .fc-widget-content[data-date='" + optionDates[i].start + "']")[0]).parents(), (parent) => {
                    return parent.className == 'fc-row fc-week fc-widget-content fc-rigid';
                }))[0];
                let currentIndex = 0;
                if ($elementWrap) {
                    let lstTd = $elementWrap.firstChild.getElementsByTagName("td");
                    for (let j = 0; j < lstTd.length; j++) {
                        if (lstTd[j].getAttribute("data-date") === optionDates[i].start) {
                            currentIndex = j;
                            break;
                        }
                    }
                    let $skeletonElement = $elementWrap.getElementsByClassName("fc-content-skeleton")[0];
                    lstTd = $elementWrap.getElementsByClassName("fc-content-skeleton")[0].getElementsByTagName("tbody")[0].getElementsByTagName("td");
                    let targetTd = lstTd[currentIndex];
                    targetTd.className = "fc-event-container";
                    //Remove old data
                    $(targetTd).empty();
                    $(targetTd).append("<a class='fc-day-grid-event fc-h-event fc-event fc-start fc-end' style='background-color:" + optionDates[i].backgroundColor + ";border-color:" + optionDates[i].backgroundColor + ";color:" + optionDates[i].textColor + "'><div class='fc-content'> <span class='fc-title'>" + displayText + "</span></div></a>");
                }  
                result = true;              
            }
            
            return result;
        }
        eventAfterAllRender(currentCalendar, lstDate, lstHoliday, lstEvent, workplaceId, workplaceName, eventUpdatable, optionDates): void {
            // no display more event
            $("#" + currentCalendar + " .fc-more").prop('onclick', null).off('click');
            // add div td-container
            let lstTdHeader = document.getElementById(currentCalendar).getElementsByClassName("fc-day-top");
            for (let i = 0; i < lstTdHeader.length; i++) {
                let tdContainer = document.createElement("div");
                $(tdContainer).append($(lstTdHeader[i]).children());
                tdContainer.className = "td-container";
                lstTdHeader[i].appendChild(tdContainer);
            }
            if (eventUpdatable) {
                // click button event
                $("#" + currentCalendar + " .td-container img").off();
                $("#" + currentCalendar + " .td-container img").on('click', function(event) {
                    event.stopPropagation();
                    nts.uk.ui.windows.setShared('eventData', { date: $(this).attr("data-date"), workplaceId: workplaceId, workplaceName: workplaceName });
                    nts.uk.ui.windows.sub.modal("at", "/view/kcp/006/b/index.xhtml", { title: '行事設定', height: 330, width: 425 }).onClosed(function(): any {
                        let fullCalendarRender = new nts.uk.at.view.kcp006.a.FullCalendarRender();
                        _lstHoliday = [];
                        _lstEvent = [];
                        fullCalendarRender.loadDataFromDB(lstDate, _lstHoliday, _lstEvent, workplaceId)
                            .done(() => {
                                fullCalendarRender.fillHolidayAndEventData(currentCalendar, _lstHoliday, _lstEvent, workplaceId);
                            });
                    });
                });
            }
            //display event note
            $("#" + currentCalendar + " .button-event").hover(function() {
                $("#" + currentCalendar + " .event-note").empty();
                $("#" + currentCalendar + " .event-note").append($("#" + currentCalendar + " .event-data[data-date='" + $(this).attr("data-date") + "']").children().clone());
                $("#" + currentCalendar + " .event-note").css("top", $(this).offset().top - 10);
                $("#" + currentCalendar + " .event-note").css("left", $(this).offset().left + 27);
                $("#" + currentCalendar + " .event-note").show();
            }, function() {
                $("#" + currentCalendar + " .event-note").hide();
            });            
            // add list text to days cell
            for (let i = 0; i < optionDates.length; i++) {
                if (optionDates[i].headerBackgroundColor) {
                    $("#" + currentCalendar + " .fc-day-top[data-date='" + optionDates[i].start + "']").attr("style", 'background-color: ' + optionDates[i].headerBackgroundColor + '!important');
                }
                let displayText = "";
                for (let j = 0; j < optionDates[i].listText.length; j++) {
                    if (optionDates[i].listText.length <= 3) {
                        displayText += optionDates[i].listText[j] + '<br/>';
                    } else {
                        if (j < 2) {
                            displayText += optionDates[i].listText[j] + '<br/>';
                        } else if (j == 2) {
                            displayText += "。。。";
                        }
                    }
                }
                displayText = displayText.replace(new RegExp('\r?\n', 'g'), '<br/>');
                let $elementWrap = $(_.find($($("#" + currentCalendar + " .fc-widget-content[data-date='" + optionDates[i].start + "']")[0]).parents(), (parent) => {
                    return parent.className == 'fc-row fc-week fc-widget-content fc-rigid';
                }))[0];
                let currentIndex = 0;
                if ($elementWrap) {
                    let lstTd = $elementWrap.firstChild.getElementsByTagName("td");
                    for (let j = 0; j < lstTd.length; j++) {
                        if (lstTd[j].getAttribute("data-date") === optionDates[i].start) {
                            currentIndex = j;
                            break;
                        }
                    }
                    let $skeletonElement = $elementWrap.getElementsByClassName("fc-content-skeleton")[0];
                    lstTd = $elementWrap.getElementsByClassName("fc-content-skeleton")[0].getElementsByTagName("tbody")[0].getElementsByTagName("td");
                    let targetTd = lstTd[currentIndex];
                    targetTd.className = "fc-event-container";
                    $(targetTd).append("<a class='fc-day-grid-event fc-h-event fc-event fc-start fc-end' style='background-color:" + optionDates[i].backgroundColor + ";border-color:" + optionDates[i].backgroundColor + ";color:" + optionDates[i].textColor + "'><div class='fc-content'> <span class='fc-title'>" + displayText + "</span></div></a>");
                }
            }
        }

        viewRender(currentCalendar, optionDates, firstDay, lstHoliday, lstEvent, eventDisplay, holidayDisplay, cellButtonDisplay, workplaceId): void {
            //customize style: add class for header
            let dateRows = $("#" + currentCalendar + " .fc-content-skeleton thead tr");
            let mappingFirstDay = [
                [0, 1, 2, 3, 4, 5, 6],
                [6, 0, 1, 2, 3, 4, 5],
                [5, 6, 0, 1, 2, 3, 4],
                [4, 5, 6, 0, 1, 2, 3],
                [3, 4, 5, 6, 0, 1, 2],
                [2, 3, 4, 5, 6, 0, 1],
                [1, 2, 3, 4, 5, 6, 0]
            ];
            for (let i = 0; i < dateRows.length; i++) {
                let numberCells = $(dateRows[i]).find("td");
                $(numberCells[mappingFirstDay[firstDay][0]]).addClass("fc-day-top fc-sun");
                $(numberCells[mappingFirstDay[firstDay][1]]).addClass("fc-day-top fc-mon");
                $(numberCells[mappingFirstDay[firstDay][2]]).addClass("fc-day-top fc-tue");
                $(numberCells[mappingFirstDay[firstDay][3]]).addClass("fc-day-top fc-wed");
                $(numberCells[mappingFirstDay[firstDay][4]]).addClass("fc-day-top fc-thu");
                $(numberCells[mappingFirstDay[firstDay][5]]).addClass("fc-day-top fc-fri");
                $(numberCells[mappingFirstDay[firstDay][6]]).addClass("fc-day-top fc-sat");
            }
            //create holiday rows
            let headers = $("#" + currentCalendar + " .fc-content-skeleton thead");
            let numberRow = $("#" + currentCalendar + " .fc-content-skeleton thead tr").clone();
            $("#" + currentCalendar + " .fc-content-skeleton thead tr").addClass("date-number");
            for (let i = 0; i < 6; i++) {
                $(numberRow[i]).addClass("holiday");
                $(headers[i]).append(numberRow[i]);
            }
            $("#" + currentCalendar + " .holiday td span").addClass("limited-label");
            $("#" + currentCalendar + " .holiday td span").html("");
            //update holiday cell
            if (holidayDisplay) {
                for (let i = 0; i < lstHoliday.length; i++) {
                    //update css
                    $("#" + currentCalendar + " .fc-day-top[data-date='" + lstHoliday[i].start + "']").addClass("holiday-header");
                    $("#" + currentCalendar + " .holiday td[data-date='" + lstHoliday[i].start + "']").addClass("holiday-header holiday-name");
                    //fill holiday name
                    $("#" + currentCalendar + " .holiday td[data-date='" + lstHoliday[i].start + "']").find("span").html(lstHoliday[i].holidayName);
                }
            }
            let currentHeaders = [];
            if (eventDisplay) {
                //create event button
                let dateHeaders = $("#" + currentCalendar + " .date-number td");
                for (let i = 0; i < dateHeaders.length; i++) {
                    if ($(dateHeaders[i]).attr("data-date")) {
                        currentHeaders.push(dateHeaders[i]);
                    }
                }
                for (let i = 0; i < currentHeaders.length; i++) {
                    $(currentHeaders[i]).append("<img class='button-event' data-date='" + $(currentHeaders[i]).attr("data-date") + "' src='/nts.uk.at.web/view/kcp/006/a/fullcalendar-lib/icon/121.png'/>");
                    $(currentHeaders[i]).append("<div class='event-data' data-date='" + $(currentHeaders[i]).attr("data-date") + "'></div>");
                }
                //fill event data to note
                for (let i = 0; i < lstEvent.length; i++) {
                    $("#" + currentCalendar + " .event-data[data-date='" + lstEvent[i].start + "']").append("<span class='com-event-label'>" + nts.uk.resource.getText("KCP006_3") + ":</span><br/>");
                    if (lstEvent[i].companyEvent) {
                        $("#" + currentCalendar + " .event-data[data-date='" + lstEvent[i].start + "']").append("<span class='com-event-content'>" + lstEvent[i].companyEvent + "</span><br/>");
                    } else {
                        $("#" + currentCalendar + " .event-data[data-date='" + lstEvent[i].start + "']").append("<span class='com-event-content'></span><br/>");
                    }
                    if (workplaceId && workplaceId !== "" && workplaceId !== "0") {
                        $("#" + currentCalendar + " .event-data[data-date='" + lstEvent[i].start + "']").append("<span class='wkp-event-label'>" + nts.uk.resource.getText("KCP006_4") + ":</span><br/>");
                        if (lstEvent[i].workplaceEvent) {
                            $("#" + currentCalendar + " .event-data[data-date='" + lstEvent[i].start + "']").append("<span class='wkp-event-content'>" + lstEvent[i].workplaceEvent + "</span>");
                        } else {
                            $("#" + currentCalendar + " .event-data[data-date='" + lstEvent[i].start + "']").append("<span class='wkp-event-content'></span>");
                        }
                    }
                    //change icon button
                    $("#" + currentCalendar + " .button-event[data-date='" + lstEvent[i].start + "']").attr("src", "/nts.uk.at.web/view/kcp/006/a/fullcalendar-lib/icon/120.png");
                }
            }
            //create event note container
            $("#" + currentCalendar + " .fc-view-container").append("<div class='event-note'></div>");
            if (cellButtonDisplay) {
                //create cell button
                let holidayHeaders = $("#" + currentCalendar + " .holiday td");
                currentHeaders = [];
                for (let i = 0; i < holidayHeaders.length; i++) {
                    if ($(holidayHeaders[i]).attr("data-date")) {
                        currentHeaders.push(holidayHeaders[i]);
                    }
                }
                for (let i = 0; i < currentHeaders.length; i++) {
                    $(currentHeaders[i]).append("<button class='button-cell' data-date='" + $(currentHeaders[i]).attr("data-date") + "'>。。。</button>");
                }
            }
            //change background color each option day
            for (let i = 0; i < optionDates.length; i++) {
                $("#" + currentCalendar + " td .fc-day[data-date='" + optionDates[i].start + "']").css("background-color", optionDates[i].backgroundColor);
            }
        }
    }
}
