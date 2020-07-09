<template>
<div class="kafs00b">
    <div v-if="params.input.mode==ScreenMode.DETAIL">
        <div class="card card-label">
            <div class="card-header" style="align-items: center">
                <v-label class="border-0 pl-0">
                    {{'KAFS00_7' | i18n}}</v-label>
            </div>
            <div class="card-body mb-2">
                <span>{{ params.input.detailModeContent.employeeName | i18n }}</span> 
            </div>
        </div>   
        <div class="card card-label" v-if="displayPrePost">
            <div class="card-header" style="align-items: center">
                <v-label class="border-0 pl-0">
                    {{'KAFS00_8' | i18n}}</v-label>
                <span class="badge badge-warning" style="height: 30%">必須</span>
            </div>
            <div class="card-body mb-2">
                <span>{{ params.input.detailModeContent.prePostAtrName | i18n }}</span> 
            </div>
        </div>
        <div class="card card-label">
            <div class="card-header" style="align-items: center">
                <v-label class="border-0 pl-0">
                    {{'KAFS00_9' | i18n}}</v-label>
                <span class="badge badge-warning" style="height: 30%">必須</span>
            </div>
            <div class="card-body mb-2">
                <span v-if="params.input.detailModeContent.startDate == params.input.detailModeContent.endDate">
                    {{ params.input.detailModeContent.startDate | i18n }}
                </span> 
                <span v-else>
                    {{ params.input.detailModeContent.startDate | i18n }} ~ {{ params.input.detailModeContent.endDate | i18n }}
                </span> 
            </div>
        </div>  
    </div>
    <div v-if="params.input.mode==ScreenMode.NEW">
        <div class="card card-label" v-if="displayPrePost">
            <div class="card-header" style="align-items: center">
                <v-label class="border-0 pl-0">
                    {{'KAFS00_8' | i18n}}</v-label>
                <span class="badge badge-warning" style="height: 30%">必須</span>
            </div>
            <div class="card-body">
                <div style="width: 100%">
                    <nts-switchbox v-for="(option, optionIndex) in datasource" v-bind:key="optionIndex"
                        v-bind:disabled="!enablePrePost"
                        v-model="params.output.prePostAtr" 
                        v-bind:value="option.code">
                            {{option.text | i18n}}
                    </nts-switchbox>
                </div>
            </div>
        </div>    
        <div class="card card-label">
            <div class="card-header" style="align-items: center">
                <v-label class="border-0 pl-0">
                    {{'KAFS00_9' | i18n}}</v-label>
                <span class="badge badge-warning" style="height: 30%">必須</span>
            </div>
            <div class="card-body">
                <div style="width: 100%" v-if="displayMultiDaySwitch">
                    <nts-switchbox v-for="(option, optionIndex) in datasource2" v-bind:key="optionIndex"
                        v-model="valueMultiDaySwitch" 
                        v-bind:value="option.code">
                            {{option.text | i18n}}
                    </nts-switchbox>
                </div>
                <div v-if="valueMultiDaySwitch">
                    <nts-date-range-input v-model="dateRange" />
                </div>
                <div v-if="!valueMultiDaySwitch">
                    <nts-date-input v-model="params.output.startDate"/>
                </div>
            </div>
        </div>
    </div>
</div>
</template>