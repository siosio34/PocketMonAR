class CreatePocketmons < ActiveRecord::Migration
  def change
    create_table :pocketmons do |t|
      t.integer :code
      t.float :latitude
      t.float :longitude
      t.integer :level

      t.timestamps null: false
    end
  end
end
