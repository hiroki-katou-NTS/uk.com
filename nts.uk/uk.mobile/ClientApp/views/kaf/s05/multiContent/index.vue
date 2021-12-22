<template>
    <div>
        <div class="card-body">
            <nts-time-range-input
                    class="mb-1"
                    v-model="params.valueHours"
            />
        </div>
        <div class="card-body" v-if="dispReason">
            <div class="mb-1">
                <span class="small-header">{{'KAFS00_16' | i18n}}</span>
            </div>
            <div v-if="displayFixedReason">
                <nts-dropdown v-model="params.fixedReasonCode">
                    <option v-for="(item, itemIndex) in dropdownList"
                            v-bind:key="itemIndex"
                            v-bind:value="item.appStandardReasonCD">
                        {{item.reasonForFixedForm | i18n}}
                    </option>
                </nts-dropdown>
            </div>
            <template v-else />
            <div v-if="displayAppReason">
                <nts-text-area v-model="params.appReason" />
            </div>
            <template v-else />
        </div>
    </div>
</template>