import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

import { browser } from '@app/utils';

@component({
    template: `<div class="datepicker-col" v-on:touchmove="preventScroll">
        <div class="datepicker-viewport"
                v-on:touchstart="startScroll"
                v-on:touchmove="onScroll"
                v-on:touchend="endScroll">
            <div class="datepicker-wheel">
                <ul class="datepicker-scroll" v-bind:style="{
                    'transform': transform,
                    'margin-top': marginTop
                }">
                    <li class v-for="(item, ik) in items" v-bind:key="ik">{{ item }}</li>
                </ul>
            </div>
        </div>
    </div>`
})
export class InfinityPickerComponent extends Vue {
    private up: boolean = false;

    private timeout: number = 0;
    private interval: number = 0;

    private move: number = 0;
    private start: number = 0;

    private moveTime: number = 0;
    private startTime: number = 0;

    private start2: number = 0;
    private startTime2: number = 0;

    @Prop({ default: () => undefined })
    private readonly value!: string | number;

    @Prop({ default: () => [] })
    private readonly dataSources!: Array<string | number>;

    public get $nindex() {
        let self = this,
            select = self.value,
            dataSources = self.dataSources,
            index = dataSources.indexOf(select),
            mgt = self.$mtNumber,
            move = Math.floor(mgt / 40),
            nidex = index + move;

        if (dataSources.length >= 5) {
            if (nidex < 0) {
                nidex = Math.abs(dataSources.length + nidex);
            } else if (nidex > dataSources.length - 1) {
                nidex = Math.abs(dataSources.length - nidex);
            }
        } else {
            if (nidex < 0) {
                nidex = 0;
            } else if (nidex > dataSources.length - 1) {
                nidex = dataSources.length - 1;
            }
        }

        return Math.floor(nidex % dataSources.length);
    }

    public get items() {
        let self = this,
            nidex = self.$nindex,
            dataSources = self.dataSources;

        if (dataSources.length >= 5) {
            let items: any[] = [];

            for (let i = nidex - 2; i <= nidex + 2; i++) {
                if (i < 0) {
                    items.push(dataSources[Math.abs(dataSources.length + i)]);
                } else if (i > dataSources.length - 1) {
                    items.push(dataSources[Math.abs(dataSources.length - i)]);
                } else {
                    items.push(dataSources[i]);
                }
            }

            return items;
        } else {
            if (nidex === 0) {
                let items: any[] = [undefined, undefined];

                for (let i = 0; i <= 2; i++) {
                    items.push(dataSources[i]);
                }

                return items;
            } else if (nidex === dataSources.length - 1) {
                let items: any[] = [undefined, undefined];

                for (let i = nidex; i >= nidex - 2; i--) {
                    items.unshift(dataSources[i]);
                }

                return items;
            } else {
                let items: any[] = [];

                for (let i = nidex - 2; i <= nidex + 2; i++) {
                    if (i < 0 || i > dataSources.length - 1) {
                        items.push(undefined);
                    } else {
                        items.push(dataSources[i]);
                    }
                }

                return items;
            }
        }
    }

    get $mtNumber() {
        let mov: number = this.move - this.start,
            amo: number = Math.abs(mov),
            mup: boolean = this.start > this.move,
            twt: boolean = (amo >= -60 && amo < -20) || (amo <= 60 && amo > 20);

        if (mov >= -20 && mov <= 20) {
            return 0;
        }

        if (mup) {
            if (twt) {
                return 40;
            } else {
                return Math.floor(amo / 40) * 40;
            }
        } else {
            if (twt) {
                return -40;
            } else {
                return -Math.floor(amo / 40) * 40;
            }
        }
    }

    get marginTop() {
        return `${this.$mtNumber}px`;
    }

    get transform() {
        return `translate3d(0px, ${this.move - this.start - 80}px, 0px)`;
    }

    private resetScroll() {
        this.move = 0;
        this.start = 0;

        this.timeout = 0;

        this.moveTime = 0;
        this.startTime = 0;

        this.start2 = 0;
        this.startTime2 = 0;
    }

    public startScroll(evt: TouchEvent) {
        evt.preventDefault();
        evt.stopPropagation();

        if (this.start === 0) {
            this.startTime = new Date().getTime();
            this.move = this.start = Math.floor(evt.changedTouches[0].clientY);
        } else {
            this.startTime2 = new Date().getTime();
            this.start2 = Math.floor(evt.changedTouches[0].clientY);
        }
    }

    public onScroll(evt: TouchEvent) {
        evt.preventDefault();
        evt.stopPropagation();

        let onTime: number = new Date().getTime(),
            moving: number = Math.floor(evt.changedTouches[0].clientY);

        if (this.timeout === 0 && onTime - this.startTime >= 200 && Math.abs(this.move - moving) > 2) {
            this.move = moving;
        }
    }

    public endScroll(evt: TouchEvent) {
        evt.preventDefault();
        evt.stopPropagation();

        let self = this,
            move = Math.floor(evt.changedTouches[0].clientY);

        self.moveTime = new Date().getTime();

        if (self.timeout === 0) {
            let gear = self.roleGear,
                time = self.moveTime - self.startTime;

            if (time > (browser.ios ? 70 : 100) && Math.abs(self.start - move) >= 40) {
                if (time < 200) {
                    gear(move);
                } else {
                    self.move = move;

                    self.$emit('input', self.dataSources[self.$nindex]);
                }
            }
        } else {
            let range: number = move - self.start2,
                times: number = new Date().getTime() - self.startTime2,
                steps: number = Math.round(Math.abs(range / times) * 100) / 100;

            if (range < 5 && times <= (browser.ios ? 70 : 100)) {
                self.$emit('input', self.dataSources[self.$nindex]);
            } else if (times > (browser.ios ? 70 : 100) && times < 200 && Math.abs(range) >= 40) {
                self.up = range < 0;

                if (steps > 4) {
                    self.timeout += 3200;
                } else if (steps > 3) {
                    self.timeout += 2800;
                } else if (steps > 2) {
                    self.timeout += 2200;
                } else if (steps > 1) {
                    self.timeout += 1200;
                } else {
                    self.timeout += 600;
                }
            }
        }
    }

    private roleGear(move: number) {
        let self = this,
            range: number = move - self.start,
            times: number = self.moveTime - self.startTime,
            steps: number = Math.round(Math.abs(range / times) * 100) / 100;

        if (self.timeout == 0) {
            self.up = range < 0;

            clearInterval(self.interval);

            if (steps > 4) {
                self.timeout = 3200;
            } else if (steps > 3) {
                self.timeout = 2800;
            } else if (steps > 2) {
                self.timeout = 2200;
            } else if (steps > 1) {
                self.timeout = 1200;
            } else {
                self.timeout = 600;
            }

            self.interval = setInterval(() => {
                if (self.timeout > 3200) {
                    self.timeout = 3200;
                }

                if (self.timeout >= 800) {
                    self.timeout -= 20;
                    self.move += self.up ? -20 : 20;
                } else if (self.timeout >= 680) {
                    self.timeout -= 20;
                    self.move += self.up ? -15 : 15;
                } else if (self.timeout >= 500) {
                    self.timeout -= 10;
                    self.move += self.up ? -10 : 10;
                } else if (self.timeout >= 460) {
                    self.timeout -= 10;
                    self.move += self.up ? -8 : 8;
                } else if (self.timeout >= 300) {
                    self.timeout -= 10;
                    self.move += self.up ? -5 : 5;
                } else if (self.timeout >= 120) {
                    self.timeout -= 5;
                    self.move += self.up ? -4 : 4;
                } else {
                    self.timeout -= 5;
                    self.move += self.up ? -2 : 2;
                }

                if (self.timeout <= 0) {
                    let index = self.$nindex;

                    self.timeout = 0;
                    clearInterval(self.interval);

                    if (Math.abs(self.move - self.start) % 40 >= 20) {
                        if (range < 0) {
                            index = (index + 1) % self.dataSources.length;
                        } else {
                            index = (index - 1) % self.dataSources.length;
                        }
                    }

                    self.$emit('input', self.dataSources[index]);
                }
            }, 30);
        }
    }

    public preventScroll(evt: TouchEvent) {
        evt.preventDefault();
        evt.stopPropagation();
    }

    public created() {
        let self = this;
        self.$on('input', () => {
            self.resetScroll.apply(self);
        });
    }
}