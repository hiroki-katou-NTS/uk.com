<template>
    <div class="kdws03c">
        <div class="modal-header uk-bg-white-smoke pl-0 pr-0 rounded-0">
            <div class="row uk-text-quote w-100 ml-0 mr-0" v-on:click="$close('NotCloseMenu')">
                <div class="col-1 pl-1"><i class="fas fa-arrow-left"></i></div>
                <div class="col-11 pl-0 pr-2"><span>{{'KDWS03_6' | i18n}}</span></div>
            </div>
        </div>
        <div class="card card-label mt-n3">
            <div class="card-header pl-0 pr-0 uk-bg-light-coral">
                <span>{{'KDWS03_39' | i18n}}</span>
            </div>
    
            <div class="card-body">
                <ul class="list-group list-group-selection">
                    <li class="list-group-item" v-for="item in displayData" v-on:click="openErrorList(item.employeeId)">{{item.employeeName}}</li>
                </ul>
            </div>
        </div>
    </div>
</template>