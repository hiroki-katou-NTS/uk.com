var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm011;
                (function (qmm011) {
                    var a;
                    (function (a) {
                        var service;
                        (function (service) {
                            var model;
                            (function (model) {
                                function convertdata(yearmonth) {
                                    var viewmonth = '';
                                    if (yearmonth.month < 0) {
                                        viewmonth = '0' + yearmonth.month;
                                    }
                                    else {
                                        viewmonth = '' + yearmonth.month;
                                    }
                                    return '' + yearmonth.year + '/' + viewmonth;
                                }
                                model.convertdata = convertdata;
                                var YearMonth = (function () {
                                    function YearMonth(year, month) {
                                        this.year = year;
                                        this.month = month;
                                    }
                                    return YearMonth;
                                }());
                                model.YearMonth = YearMonth;
                                var MonthRange = (function () {
                                    function MonthRange(startMonth, endMonth) {
                                        this.startMonth = startMonth;
                                        this.endMonth = endMonth;
                                    }
                                    return MonthRange;
                                }());
                                model.MonthRange = MonthRange;
                                var RoundingMethod = (function () {
                                    function RoundingMethod(code, name) {
                                        this.code = code;
                                        this.name = name;
                                    }
                                    return RoundingMethod;
                                }());
                                model.RoundingMethod = RoundingMethod;
                            })(model = service.model || (service.model = {}));
                        })(service = a.service || (a.service = {}));
                    })(a = qmm011.a || (qmm011.a = {}));
                })(qmm011 = view.qmm011 || (view.qmm011 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
