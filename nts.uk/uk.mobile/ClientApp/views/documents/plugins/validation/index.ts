import { $ } from '@app/utils';
import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'documentspluginsvalidation',
    route: {
        url: '/plugins/validation',
        parent: '/documents'
    },
    template: require('./index.vue'),
    resource: require('./resources.json'),
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/jp.md')
    },
    validations: {
        model: {
            username: {
                required: true,
                constraint: 'SampleIntMinMax'
            },
            password: {
                required: true,
                constraint: 'SampleStringKana'
            },
            items: {
                name: {
                    loop: true,
                    required: true,
                    constraint: 'SampleStringKana'
                },
                age: {
                    loop: true,
                    required: true,
                    constraint: 'SampleStringAnyHalf'
                }
            }
        }
    },
    constraints: [
        'nts.uk.shr.sample.primitive.decimals.SampleDecimalMinMax',
        'nts.uk.shr.sample.primitive.decimals.SampleDecimalRange',
        'nts.uk.shr.sample.primitive.halfints.SampleHalfIntMinMax',
        'nts.uk.shr.sample.primitive.halfints.SampleHalfIntRange',
        'nts.uk.shr.sample.primitive.ints.SampleIntMinMax',
        'nts.uk.shr.sample.primitive.ints.SampleIntRange',
        'nts.uk.shr.sample.primitive.longs.SampleLongMinMax',
        'nts.uk.shr.sample.primitive.longs.SampleLongRange',

        'nts.uk.shr.sample.primitive.strings.SampleStringAlphaNumeric',
        'nts.uk.shr.sample.primitive.strings.SampleStringAny',
        'nts.uk.shr.sample.primitive.strings.SampleStringAnyHalf',
        'nts.uk.shr.sample.primitive.strings.SampleStringKana',
        'nts.uk.shr.sample.primitive.strings.SampleStringNumeric',

        'nts.uk.shr.sample.primitive.times.SampleClockMinMax',
        'nts.uk.shr.sample.primitive.times.SampleClockRange',
        'nts.uk.shr.sample.primitive.times.SampleDurationMinMax',
        'nts.uk.shr.sample.primitive.times.SampleDurationRange'
    ]
})
export class DocumentsPluginsValidationComponent extends Vue {
    public model: {
        username: number;
        password: string;
        items: any[];
    } = {
            username: 500,
            password: 'password',
            items: [{
                name: 'a',
                age: 100
            }, {
                name: 'b',
                age: 100
            }, {
                name: 'c',
                age: 100
            }, {
                name: 'd',
                age: 100
            }, {
                name: 'e',
                age: 100
            }]
        };

    public created() {
        $.set(window, '__', $);
        $.set(window, 'vm', this);
    }
}