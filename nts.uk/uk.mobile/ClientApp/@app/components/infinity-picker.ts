/*!
 * Copyright 2018
 */

import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

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

    public startScroll(evt: TouchEvent) {
        evt.preventDefault();
        evt.stopPropagation();

        this.move = this.start = Math.floor(evt.changedTouches[0].clientY);
    }

    public onScroll(evt: TouchEvent) {
        evt.preventDefault();
        evt.stopPropagation();

        this.move = Math.floor(evt.changedTouches[0].clientY);

        if (evt.changedTouches[0].clientY < 1) {
            this.endScroll(evt);
        }
    }

    public endScroll(evt: TouchEvent) {
        evt.preventDefault();
        evt.stopPropagation();

        console.log(evt.changedTouches[0].clientY);

        this.$emit('input', this.dataSources[this.$nindex]);

        this.$nextTick(() => {
            this.move = 0;
            this.start = 0;
        });
    }

    public preventScroll(evt: TouchEvent) {
        evt.preventDefault();
        evt.stopPropagation();
    }
}