/// <reference path="../../reference.ts"/>

module nts.uk.time.minutesBased {
    
    export module clock {
        
        export interface ClockMinutesBasedTime extends MinutesBasedTime<ClockMinutesBasedTime> {
            daysOffset: number;
            hourPart: number;
            minutePart: number;
            clockTextInDay: string;
        }
        
        /**
         * create new instance
         */
        export function create(daysOffset: number, hourPart: number, minutePart: number): ClockMinutesBasedTime;
        export function create(minutesFromZeroOclock: number): ClockMinutesBasedTime;
        export function create(... args: any[]): ClockMinutesBasedTime {
            let timeAsMinutes = parseAsClock(args);
            let clock: any = createBase(timeAsMinutes);
            
            let positivizedMinutes = () => (timeAsMinutes >= 0)
                    ? timeAsMinutes
                    : timeAsMinutes + (1 + Math.floor(-timeAsMinutes / MINUTES_IN_DAY)) * MINUTES_IN_DAY;
            
            let daysOffset = () => ntsNumber.trunc(
                    clock.isNegative ? (timeAsMinutes + 1) / MINUTES_IN_DAY - 1
                            : timeAsMinutes / MINUTES_IN_DAY);
            
            util.accessor.defineInto(clock)
                .get("typeName", () => "ClockMinutesBasedTime")
                .get("daysOffset", () => daysOffset())
                .get("hourPart", () => Math.floor((positivizedMinutes() % MINUTES_IN_DAY) / 60))
                .get("minutePart", () => positivizedMinutes() % 60)
                .get("clockTextInDay", () => clock.hourPart + ":" + text.padLeft(clock.minutePart.toString(), "0", 2));
            
            return clock;
        }
        
        function parseAsClock(args: any[]): number {
            var result: number;
            
            if (types.matchArguments(args, ["number"])) {
                result = args[0];
            }
            else if (types.matchArguments(args, ["number", "number", "number"])) {
                let daysOffset: number = args[0];
                let hourPart: number = args[1];
                let minutePart: number = args[2];
                result = daysOffset * MINUTES_IN_DAY + hourPart * 60 + minutePart;
            }
            
            return result;
        }
    }
}