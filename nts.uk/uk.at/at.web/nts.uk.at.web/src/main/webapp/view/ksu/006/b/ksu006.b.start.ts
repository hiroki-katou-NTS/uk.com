module nts.uk.pr.view.ksu006.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            let extractCondition: any = nts.uk.ui.windows.getShared("ExtractCondition");
            screenModel.status(nts.uk.resource.getText("KSU006_216"));
            $('.countdown').downCount();
//            nts.uk.ui.block.invisible();
            service.executeImportFile(extractCondition).then(function(res: any) {
                screenModel.executeId(res.executeId);
                $('#stopExecute').focus();
                nts.uk.deferred.repeat(conf => conf
                .task(() => {
                    let dfd = $.Deferred();
                    nts.uk.request.specials.getAsyncTaskInfo(res.taskId).done(function(res: any) {
                        if (res.running || res.succeeded || res.failed) {
                            _.forEach(res.taskDatas, item => {
                                if (item.key == 'TOTAL_RECORD') {
                                    screenModel.totalRecord(item.valueAsNumber);
                                }
                                if (item.key == 'SUCCESS_CNT') {
                                    screenModel.numberSuccess(item.valueAsNumber);
                                }
                                if (item.key == 'FAIL_CNT') {
                                    screenModel.numberFail(item.valueAsNumber);
                                }
                            });
                        }
                        if (res.succeeded || res.failed) {
                            screenModel.isDone(true);
                            screenModel.status(nts.uk.resource.getText("KSU006_217"));
                            $('.countdown').stop();
                            if (res.error) {
                                nts.uk.ui.dialog.alertError(res.error.message);
                            }
//                            nts.uk.ui.block.clear();
                            if (res.succeeded) {
                                $('#closeDialog').focus();
                            }
                        }
                        dfd.resolve(res);
                    });
                    return dfd.promise();
                }).while(info => {
                    return info.pending || info.running;
                })
                .pause(1000))
            })
            .done(function(res: any) {
            })
            .fail(function(res: any) {
                nts.uk.ui.dialog.alertError(res.message);
            });
        });
    });
}
interface JQuery {

    downCount(options, callback);
    stop(options, callback);
}

(function($: any) {
    let interval;
    $.fn.downCount = function(options, callback) {
        let settings = $.extend({
            date: null,
            offset: null
        }, options);

        // Save container
        let container = this;

        /**
         * Change client's local date to match offset timezone
         * @return {Object} Fixed Date object.
         */
        let currentDate = function() {
            // get client's current date
            let date = new Date();

            // turn date to utc
            let utc = date.getTime() + (date.getTimezoneOffset() * 60000);

            // set new Date object
            let new_date = new Date(utc + (3600000 * settings.offset))

            return new_date;
        };

        /**
         * Main downCount function that calculates everything
         */
        let original_date = currentDate();
        let target_date = new Date('12/31/2020 12:00:00'); // Count up to this date

        function onButtonClick() {
            original_date = currentDate();
        }

        function countdown() {
            let current_date = currentDate(); // get fixed current date

            // difference of dates
            let difference = current_date - original_date;

            if (current_date >= target_date) {
                // stop timer
                clearInterval(interval);

                if (callback && typeof callback === 'function') callback();

                return;
            }

            // basic math variables
            let _second = 1000,
                _minute = _second * 60,
                _hour = _minute * 60,
                _day = _hour * 24;

            // calculate dates
//            days = Math.floor(difference / _day)
            let hours = Math.floor((difference % _day) / _hour),
                minutes = Math.floor((difference % _hour) / _minute),
                seconds = Math.floor((difference % _minute) / _second);

            // fix dates so that it will show two digets
//            days = (String(days).length >= 2) ? days : '0' + days;
            hours = (String(hours).length >= 2) ? hours : '0' + hours;
            minutes = (String(minutes).length >= 2) ? minutes : '0' + minutes;
            seconds = (String(seconds).length >= 2) ? seconds : '0' + seconds;

            // based on the date change the refrence wording
//            ref_days = (days === 1) ? 'day' : 'days',
            let ref_hours = (hours === 1) ? 'hour' : 'hours',
                ref_minutes = (minutes === 1) ? 'minute' : 'minutes',
                ref_seconds = (seconds === 1) ? 'second' : 'seconds';

            // set to DOM
//            container.find('.days').text(days);
            container.find('.hours').text(hours);
            container.find('.minutes').text(minutes);
            container.find('.seconds').text(seconds);

//            container.find('.days_ref').text(ref_days);
            container.find('.hours_ref').text(ref_hours);
            container.find('.minutes_ref').text(ref_minutes);
            container.find('.seconds_ref').text(ref_seconds);
        };

        // start
        interval = setInterval(countdown, 1000);
    };
    
    $.fn.stop = function(options, callback) {
         clearInterval(interval);
    };
} (jQuery));



