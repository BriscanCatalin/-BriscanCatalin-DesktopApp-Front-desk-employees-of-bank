package repository.client_info;

public abstract class Client_InfoRepositoryDecorator implements Client_InfoRepository{

    protected Client_InfoRepository client_infoRepository;

    public Client_InfoRepositoryDecorator(Client_InfoRepository client_infoRepository) {
        this.client_infoRepository = client_infoRepository;
    }
}
