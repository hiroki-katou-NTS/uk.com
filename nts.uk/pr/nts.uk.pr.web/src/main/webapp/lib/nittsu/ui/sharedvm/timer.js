var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var sharedvm;
            (function (sharedvm) {
                var KibanTimer = (function () {
                    function KibanTimer(target) {
                        var self = this;
                        self.elapsedSeconds = 0;
                        self.formatted = ko.observable(nts.uk.text.formatSeconds(this.elapsedSeconds, 'hh:mm:ss'));
                        self.targetComponent = target;
                        self.isTimerStart = ko.observable(false);
                        self.oldDated = ko.observable(undefined);
                        document.getElementById(self.targetComponent).innerHTML = self.formatted();
                    }
                    KibanTimer.prototype.run = function () {
                        //            var x = self.getTime(new Date()) - self.getTime(self.oldDated());
                        var x = new Date().getTime() - this.oldDated().getTime();
                        x = Math.floor(x / 1000);
                        this.elapsedSeconds = x;
                        //            self.fomatted(nts.uk.text.formatSeconds(self.elapsedSeconds(), 'hh:mm:ss'));
                        //            self.fomatted(x);
                        //            $(self.targetComponent).html(x.toString());
                        document.getElementById(this.targetComponent).innerHTML
                            = nts.uk.text.formatSeconds(x, 'hh:mm:ss');
                    };
                    KibanTimer.prototype.getTime = function (value) {
                        var self = this;
                        var day = value.getDate();
                        var hours = value.getHours();
                        var minutes = value.getMinutes();
                        var seconds = value.getSeconds();
                        var time = day * 24 * 60 * 60 + hours * 60 * 60 + minutes * 60 + seconds;
                        return time;
                    };
                    KibanTimer.prototype.start = function () {
                        var _this = this;
                        var self = this;
                        if (!self.isTimerStart()) {
                            self.oldDated(new Date());
                            self.isTimerStart(true);
                            self.interval = setInterval(function () { return _this.run(); }, 1000);
                        }
                    };
                    KibanTimer.prototype.end = function () {
                        var self = this;
                        if (self.isTimerStart()) {
                            self.oldDated(undefined);
                            self.isTimerStart(false);
                            clearInterval(self.interval);
                        }
                    };
                    return KibanTimer;
                }());
                sharedvm.KibanTimer = KibanTimer;
            })(sharedvm = ui.sharedvm || (ui.sharedvm = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
