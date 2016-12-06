module nts.uk.ui.file {

    export class FileDownload {
        servicePath: string;
        data: any;
        isFinishTask: KnockoutObservable<boolean>;
        taskId: any;
        interval: any;

        constructor(servicePath: string, data?: any) {
            var self = this;
            self.servicePath = servicePath;
            self.data = data;
            self.isFinishTask = ko.observable(false);
            self.isFinishTask.subscribe(function(value){
                if(value){
                    clearInterval(self.interval);
                    self.isFinishTask(false);
                    self.download();    
                }
            });
        }

        isTaskFinished(file: any) {
            var options = {
                dataType: 'text',
                contentType: 'text/plain'
            };
            request.ajax("file/file/isfinished", file.taskId).done(function(res){
                file.isFinishTask(res);
            });
        }

        print() {
            var self = this;
            var options = {
                dataType: 'text',
                contentType: 'text/plain'
            };
            request.ajax(self.servicePath, self.data, options).done(function(res) {
                self.taskId = res;
                self.interval = setInterval(self.isTaskFinished, 1000, self);
            });
        }
        
        download(){
            var self = this;
            window.location.href = ("http://localhost:8080/nts.uk.pr.web/webapi/file/file/downloadreport/" + self.taskId);
        }
    }
}