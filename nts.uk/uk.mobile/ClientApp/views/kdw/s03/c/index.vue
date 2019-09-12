<template>
    <div class="kdws03c">
        <div class="card card-label">   
            <div class="card-header">
                <span>{{'KDWS03_39' | i18n}}</span>
            </div>
    
            <div class="card-body">
                <ul class="list-group list-group-selection">
                    <li class="list-group-item" v-for="item in displayData">{{item.employeeName}}</li>
                </ul>
            </div>
        </div>
    </div>
</template>