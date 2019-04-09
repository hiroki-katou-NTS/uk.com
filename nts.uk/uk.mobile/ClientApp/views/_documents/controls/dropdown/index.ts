import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: {
        url: '/controls/dropdown',
        parent: '/documents'
    },
    template: require('./index.html'),
    markdown: {
        jp: require('./content/jp.md'),
        vi: require('./content/vi.md')
    }
})
export class DropdownControl extends Vue {

    dropdownList: Array<Object> = [{
        code: 1,
        text: "The First"
    }, {
        code: 2,
        text: "The Second"
    }, {
        code: 3,
        text: "The Third"
    }, {
        code: 4,
        text: "The Fourth"
    },{
        code: 5,
        text: "The Fifth"
    }];

    selectedValue: number = 3;

}