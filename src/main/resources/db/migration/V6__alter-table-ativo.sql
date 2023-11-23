
ALTER TABLE medicos MODIFY COLUMN ativo TINYINT null;
update medicos set ativo = 1;
