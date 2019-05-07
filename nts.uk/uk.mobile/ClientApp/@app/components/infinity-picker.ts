import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    template: `<div class="datepicker-col" v-on:touchmove="preventScroll">
        <div class="datepicker-viewport"
                v-on:touchstart="gearTouchStart"
                v-on:touchmove="gearTouchMove"
                v-on:touchend="gearTouchEnd">
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
    private speed: number = 0;
    private interval: number = 0;

    private time: {
        old: number;
        new: number;
    } = {
            old: 0,
            new: 0
        };

    private position: {
        old: number;
        new: number;
    } = {
            old: 0,
            new: 0
        };

    private move: number = 0;
    private start: number = 0;

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

    public gearTouchStart(evt: TouchEvent) {
        let self = this;

        evt.preventDefault();
        evt.stopPropagation();

        clearInterval(self.interval);

        // if continue touch, emit current select item
        if (self.value !== self.dataSources[self.$nindex]) {
            self.$emit('input', self.dataSources[self.$nindex]);
        }

        self.time.old = new Date().getTime();
        self.position.old = Math.floor(evt.changedTouches[0].clientY);

        self.start = self.move = Math.floor(evt.changedTouches[0].clientY);
    }

    public gearTouchMove(evt: TouchEvent) {
        let self = this;

        evt.preventDefault();
        evt.stopPropagation();

        self.time.new = new Date().getTime();
        self.move = self.position.new = Math.floor(evt.changedTouches[0].clientY);

        if (evt.targetTouches[0].screenY < 1) {
            self.gearTouchEnd(evt);
        }
    }

    public gearTouchEnd(evt: TouchEvent) {
        let self = this;

        evt.preventDefault();
        evt.stopPropagation();

        let flag = (self.position.new - self.position.old) / (self.time.new - self.time.old);

        if (Math.abs(flag) < 1) {
            self.speed = 0;
        } else if (Math.abs(flag) <= 2) {
            self.speed = flag < 0 ? -20 : 20;
        } else {
            self.speed = flag < 0 ? -40 : 40;
        }

        if (self.dataSources.length >= 5) {
            self.roleGear();
        } else {
            self.$emit('input', self.dataSources[self.$nindex]);
        }
    }

    private roleGear() {
        let self = this,
            d: number = 0;

        clearInterval(self.interval);

        self.interval = setInterval(() => {
            let speed = Math.round(self.speed * Math.exp(-0.03 * d));

            self.move += speed;

            if (speed > -1 && speed < 1) {
                clearInterval(self.interval);

                let index = self.$nindex,
                    steps = Math.abs(self.move - self.start) % 40;

                if (steps >= 20) {
                    if (self.move > self.start) {
                        index--;
                    } else {
                        index++;
                    }
                }

                self.$emit('input', self.dataSources[index]);
            }

            d++;
        }, 30);
    }

    public preventScroll(evt: TouchEvent) {
        evt.preventDefault();
        evt.stopPropagation();
    }

    public created() {
        let self = this;

        // reset position of gear
        self.$on('input', () => {
            self.move = 0;
            self.start = 0;
        });
    }
}