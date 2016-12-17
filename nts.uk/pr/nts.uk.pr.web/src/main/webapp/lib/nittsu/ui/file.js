var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var file;
            (function (file_1) {
                var FileDownload = (function () {
                    function FileDownload(servicePath, data) {
                        var self = this;
                        self.servicePath = servicePath;
                        self.data = data;
                        self.isFinishTask = ko.observable(false);
                        self.isFinishTask.subscribe(function (value) {
                            if (value) {
                                clearInterval(self.interval);
                                self.isFinishTask(false);
                                self.download();
                            }
                        });
                    }
                    FileDownload.prototype.isTaskFinished = function (file) {
                        var options = {
                            dataType: 'text',
                            contentType: 'text/plain'
                        };
                        uk.request.ajax("file/file/isfinished", file.taskId).done(function (res) {
                            file.isFinishTask(res);
                        });
                    };
                    FileDownload.prototype.print = function () {
                        var self = this;
                        var options = {
                            dataType: 'text',
                            contentType: 'application/json'
                        };
                        uk.request.ajax(self.servicePath, self.data, options).done(function (res) {
                            self.taskId = res;
                            self.interval = setInterval(self.isTaskFinished, 1000, self);
                        });
                    };
                    FileDownload.prototype.download = function () {
                        var self = this;
                        window.location.href = ("http://localhost:8080/nts.uk.pr.web/webapi/file/file/downloadreport/" + self.taskId);
                    };
                    return FileDownload;
                }());
                file_1.FileDownload = FileDownload;
            })(file = ui.file || (ui.file = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
