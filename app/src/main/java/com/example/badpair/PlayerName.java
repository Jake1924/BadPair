package com.example.badpair;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

    @Entity(tableName = "player_table")
    public class PlayerName {

        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "name")
        private String name;

        public  PlayerName(@NonNull String name) { this.name = name;}

        public String getName(){return this.name;}
    }

