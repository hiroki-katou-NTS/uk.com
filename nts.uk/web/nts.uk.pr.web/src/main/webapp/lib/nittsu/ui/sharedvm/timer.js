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
                        self.formatted = ko.observable(uk.time.formatSeconds(this.elapsedSeconds, 'hh:mm:ss'));
                        self.targetComponent = target;
                        self.isTimerStart = ko.observable(false);
                        self.oldDated = ko.observable(undefined);
                        document.getElementById(self.targetComponent).innerHTML = self.formatted();
                    }
                    KibanTimer.prototype.run = function (timer) {
                        var x = new Date().getTime() - timer.oldDated().getTime();
                        x = Math.floor(x / 1000);
                        timer.elapsedSeconds = x;
                        document.getElementById(timer.targetComponent).innerHTML
                            = uk.time.formatSeconds(x, 'hh:mm:ss');
                    };
                    KibanTimer.prototype.start = function () {
                        var self = this;
                        if (!self.isTimerStart()) {
                            self.oldDated(new Date());
                            self.isTimerStart(true);
                            self.interval = setInterval(self.run, 1000, self);
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
//# sourceMappingURL=timer.js.map