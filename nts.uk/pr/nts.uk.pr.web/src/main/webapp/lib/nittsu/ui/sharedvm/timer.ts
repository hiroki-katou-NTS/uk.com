module nts.uk.ui.sharedvm {

    export class KibanTimer {
        elapsedSeconds: number;
        fomatted: KnockoutObservable<string>;
        targetComponent: string;
        isTimerStart: KnockoutObservable<boolean>;
        oldDated: KnockoutObservable<Date>;
        interval: any;

        constructor(target: string) {
            var self = this;
            self.elapsedSeconds = 0;
            self.fomatted = ko.observable(nts.uk.text.formatSeconds(this.elapsedSeconds, 'hh:mm:ss'));
            self.targetComponent = target;
            self.isTimerStart = ko.observable(false);
            self.oldDated = ko.observable(undefined);
        }
        run() {
//            var x = self.getTime(new Date()) - self.getTime(self.oldDated());
            var x = new Date().getTime() - this.oldDated().getTime();
            x = Math.floor(x/1000);
            this.elapsedSeconds = x;
//            self.fomatted(nts.uk.text.formatSeconds(self.elapsedSeconds(), 'hh:mm:ss'));
//            self.fomatted(x);
//            $(self.targetComponent).html(x.toString());
            document.getElementById(this.targetComponent).innerHTML 
                = nts.uk.text.formatSeconds(x, 'hh:mm:ss');
        }
        
        getTime(value: Date){
            var self = this;
            
            var day = value.getDate();
            var hours = value.getHours();
            var minutes = value.getMinutes();
            var seconds = value.getSeconds();
            
            var time = day*24*60*60 + hours*60*60 + minutes*60 + seconds;
            return time;
        }
        
        start() {
            var self = this;
            if(!self.isTimerStart()){
                self.oldDated(new Date());
                self.isTimerStart(true); 
                self.interval = setInterval(() => this.run(), 1000);
            }
        }

        end() {
            var self = this;
            if(self.isTimerStart()){
                self.oldDated(undefined);
                self.isTimerStart(false);
                clearInterval(self.interval);    
            }
        }

    }
}